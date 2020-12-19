package io.github.hooj0.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 状态固定装置
 * @Setup 用于基准测试前的初始化动作
 * @TearDown 用于基准测试后的动作
 *
 * @author hoojo
 * @version 1.0
 * @date 2022/02/17 16:18:04
 */
@State(Scope.Thread)
public class JMHSample_05_StateFixtures {

    double x;

    /**  初始化动作 */
    @Setup
    public void prepare() {
        this.x = Math.PI;
    }

    /** 测试后的动作 */
    @TearDown
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
                .include(JMHSample_05_StateFixtures.class.getSimpleName())
                .forks(1)
                .jvmArgs("-ea")
                .build();

        new Runner(options).run();
    }
}
