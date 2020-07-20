package io.github.hooj0.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 基准模式
 *
 * JMH 在基准测试编译期间为您生成大量用于基准测试的合成代码。
 * JMH 可以在多种模式下测量基准方法。
 * 用户可以选择带有特殊注释的默认基准模式，或者通过运行时选项选择覆盖模式。
 * 在这种情况下，我们开始测量一些有用的东西。
 * 请注意，我们的有效负载代码可能会抛出异常，我们可以声明它们被抛出。
 * 如果代码抛出实际异常，则基准测试执行将因错误而停止。
 * 当您对某些特定行为感到困惑时，查看生成的代码通常会有所帮助。
 * 您可能会看到代码没有做您打算做的事情。
 * 好的实验总是跟进实验设置，交叉检查生成的代码是跟进的重要部分。
 *
 * @OutputTimeUnit 可以指定输出的时间单位，可以传入 java.util.concurrent.TimeUnit 中的时间单位，最小可以到纳秒级别；
 * @BenchmarkMode 指明了基准测试的模式
 * Mode.Throughput ：吞吐量，单位时间内执行的次数
 * Mode.AverageTime：平均时间，一次执行需要的单位时间，其实是吞吐量的倒数
 * Mode.SampleTime：是基于采样的执行时间，采样频率由JMH自动控制，同时结果中也会统计出p90、p95的时间
 * Mode.SingleShotTime：单次执行时间，只执行一次，可用于冷启动的测试
 *
 * @author hoojo
 * @version 1.0
 * @date 2022/02/17 11:17:54
 */
public class JMHSample_02_BenchmarkModes {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureThroughput() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100L);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureAverageTime() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100L);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureSampleTime() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100L);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureSingleShotTime() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100L);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureMultiple() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100L);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureAll() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100L);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHSample_02_BenchmarkModes.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
