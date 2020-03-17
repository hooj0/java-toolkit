package io.github.hooj0.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 状态
 * 大多数情况下，您需要在基准运行时保持一些状态。
 * 由于 JMH 大量用于构建并发基准测试，因此我们选择了明确的状态承载对象概念。
 * 下面是两个状态对象。
 * 它们的类名不是必需的，重要的是它们用@State 标记。
 * 这些对象将按需实例化，并在整个基准测试期间重用。
 * 重要的属性是状态总是由这些基准线程之一实例化，然后该线程将有权访问该状态。
 * 这意味着您可以像在工作线程中那样初始化字段（ThreadLocals ）。
 * 注解 @State 的用法，用于多线程的测试：
 *
 * @author hoojo
 * @version 1.0
 * @State(Scope.Thread)： 作用域为线程，可以理解为一个ThreadLocal变量
 * @State(Scope.Benchmark)： 作用域为本次JMH测试，线程共享
 * @State(Scope.Group)： 作用域为group，将在后文看到
 * 而且JMH可以像spring一样自动注入这些变量。
 * @date 2022/02/17 15:44:30
 */
public class JMHSample_03_States {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        volatile double x = Math.PI;
    }

    @State(Scope.Thread)
    public static class ThreadState {
        volatile double x = Math.PI;
    }

    @Benchmark
    public void measureUnshared(ThreadState state) {
        state.x++;
    }

    @Benchmark
    public void measureShared(BenchmarkState state) {
        state.x++;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHSample_03_States.class.getSimpleName())
                .threads(4)
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
