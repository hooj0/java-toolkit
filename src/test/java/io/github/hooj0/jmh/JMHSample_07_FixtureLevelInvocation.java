package io.github.hooj0.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.*;

/**
 * 级别调用
 * 使用Level.Invocation达到每次方法执行完成后sleep一段时间，
 * 模拟在需要唤醒线程的情况下耗时更多。
 *
 * @author hoojo
 * @version 1.0
 * @date 2022/02/17 17:40:18
 */
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class JMHSample_07_FixtureLevelInvocation {

    @State(Scope.Benchmark)
    public static class NormalState {

        ExecutorService service;

        @Setup(Level.Trial)
        public void up() {
            this.service = Executors.newCachedThreadPool();
        }

        @TearDown(Level.Trial)
        public void down() {
            this.service.shutdown();
        }
    }

    public static class LaggingState extends NormalState {
        private static final Integer SLEEP_TIME = Integer.getInteger("sleep_time", 10);

        @Setup(Level.Invocation)
        public void lag() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
        }
    }

    @State(Scope.Thread)
    public static class Scratch {
        double p;

        public double doWork() {
            this.p = Math.log(this.p);
            return this.p;
        }
    }

    public static class Task implements Callable<Double> {

        private final Scratch s;

        public Task(Scratch s) {
            this.s = s;
        }

        @Override
        public Double call() throws Exception {
            return this.s.doWork();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public double measureHot(NormalState state, Scratch scratch) throws ExecutionException, InterruptedException {
        return state.service.submit(new Task(scratch)).get();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public double measureCold(LaggingState state, Scratch scratch) throws ExecutionException, InterruptedException {
        return state.service.submit(new Task(scratch)).get();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHSample_07_FixtureLevelInvocation.class.getSimpleName())
                .forks(1)
                //.jvmArgs("-Dsleep_time=100")
                .build();

        new Runner(options).run();
    }
}
