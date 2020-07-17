package io.github.hooj0.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 编译器消除
 * 解决（8）中死码消除问题，JMH提供了一个 Blackholes （黑洞），这样写就不会被编译器消除了。
 *
 * @author hoojo
 * @version 1.0
 * @date 2022/02/18 15:38:11
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHSample_09_Blackholes {

    private final double x = Math.PI;
    private final double x2 = Math.PI * 2;

    private double compute(double d) {
        for (int c = 0; c < 10; c++) {
            d = d * d / Math.PI;
        }
        return d;
    }

    @Benchmark
    public void baseline() {
    }

    @Benchmark
    public void measureWrong() {
        this.compute(this.x);
    }

    @Benchmark
    public double measureRight() {
        return this.compute(this.x);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHSample_08_DeadCode.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
