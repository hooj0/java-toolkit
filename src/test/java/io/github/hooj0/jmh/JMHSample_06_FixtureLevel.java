package io.github.hooj0.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 装置级别
 * @Setup 和 @TearDown两个注解都可以传入 Level 参数，Level参数表明粒度，粒度从粗到细分别是
 * Level.Trial：Benchmark级别
 * Level.Iteration：执行迭代级别
 * Level.Invocation：每次方法调用级别
 *
 * @author hoojo
 * @version 1.0
 * @date 2022/02/17 16:37:58
 */
@State(Scope.Thread)
public class JMHSample_06_FixtureLevel {

    double x;

    @TearDown(Level.Iteration)
    public void check() {
        assert this.x > Math.PI : "Nothing changed?";
    }

    @Benchmark
    public void measureRight() {
        this.x++;
    }

    @Benchmark
    public void measureWrong() {
        double x = 0;
        x++;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHSample_06_FixtureLevel.class.getSimpleName())
                .forks(1)
                .jvmArgs("-ea")
                .shouldFailOnError(false) // switch to "true" to fail the complete run
                .build();

        new Runner(options).run();
    }
}
