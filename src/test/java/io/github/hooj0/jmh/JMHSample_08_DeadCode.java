package io.github.hooj0.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Dead-Code Elimination (DCE) ，即死码消除，
 * 文档上说编译器非常聪明，有的代码没啥用，就在编译器被消除了，
 * 但这给我做基准测试带了一些麻烦，
 * 比如上面的代码中，baseline 和 measureWrong 有着相同的性能，
 * 因为编译器觉得 measureWrong这段代码执行后没有任何影响，为了效率，就直接消除掉这段代码，
 * 但是如果加上return语句，就不会在编译期被去掉，这是我们在写基准测试时需要注意的点。
 *
 * @author hoojo
 * @version 1.0
 * @date 2022/2/18 0018 14:43
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHSample_08_DeadCode {

    private final double x = Math.PI;

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
