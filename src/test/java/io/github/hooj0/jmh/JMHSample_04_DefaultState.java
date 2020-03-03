package io.github.hooj0.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 默认状态
 *
 * @author hoojo
 * @version 1.0
 * @State 注解可以直接写在 Benchmark 的测试类上，表明类的所有属性的作用域。
 * @date 2022/02/17 16:00:07
 */
@State(Scope.Thread)
public class JMHSample_04_DefaultState {

    double x = Math.PI;

    @Benchmark
    public void measure() {
        this.x++;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHSample_04_DefaultState.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
