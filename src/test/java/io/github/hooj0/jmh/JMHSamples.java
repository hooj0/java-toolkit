package io.github.hooj0.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * JMH sample 注解介绍
 *
 * <code>
 *     <li>@BenchmarkMode - 微基准测试类型</li>
 *      Throughput - 每段时间执行的次数，一般是秒 <br/>
 *      AverageTime - 平均时间，每次操作的平均耗时 <br/>
 *      SampleTime - 在测试中，随机进行采样执行的时间 <br/>
 *      SingleShotTime - 在每次执行中计算耗时 <br/>
 *      All - 所有模式 <br/>
 *
 *     <li>@Warmup - iterations = 3就是指预热轮数</li>
 *     <li>@Measurement - 正式度量计算的轮数</li>
 *     <li>@Threads - 每个进程中的测试线程</li>
 *     <li>@Fork - 进行 fork 的次数。如果 fork 数是3的话，则 JMH 会 fork 出3个进程来进行测试</li>
 *     <li>@OutputTimeUnit - 基准测试结果的时间类型。一般选择秒、毫秒、微秒。</li>
 *     <li>@Benchmark - 方法级注解，表示该方法是需要进行 benchmark 的对象，用法和 JUnit 的 @Test 类似</li>
 *     <li>@Param - 属性级注解，@Param 可以用来指定某项参数的多种情况。特别适合用来测试一个函数在不同的参数输入的情况下的性能。</li>
 *     <li>@Setup - 方法级注解，这个注解的作用就是我们需要在测试之前进行一些准备工作，比如对一些数据的初始化之类的。</li>
 *     <li>@TearDown - 方法级注解，这个注解的作用就是我们需要在测试之后进行一些结束工作，比如关闭线程池，数据库连接等的，主要用于资源的回收等。</li>
 *     <li>@State - 当使用@Setup参数的时候，必须在类上加这个参数，不然会提示无法运行。</li>
 *      State 用于声明某个类是一个“状态”，然后接受一个 Scope 参数用来表示该状态的共享范围。因为很多 benchmark 会需要一些表示状态的类，JMH 允许你把这些类以依赖注入的方式注入到 benchmark 函数里。Scope 主要分为三种。<br/>
 *      Thread - 该状态为每个线程独享 <br/>
 *      Group - 该状态为同一个组里面所有线程共享 <br/>
 *      Benchmark - 该状态在所有线程间共享 <br/>
 *     <li>@CompilerControl - 编译控制</li>
 *      强制使用内联（INLINE）
 *      禁止使用内联（DONT_INLINE）
 *      禁止方法编译（EXCLUDE）
 * </code>
 *
 * https://mp.weixin.qq.com/s/36ICuAz1GhA4shyWCKDq4Q
 * @author hoojo
 * @version 1.0
 * @date 2022/2/24 0024 14:28
 */
@BenchmarkMode(Mode.All)
@Threads(Threads.MAX)
@OutputTimeUnit(TimeUnit.SECONDS)
public class JMHSamples {

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.SingleShotTime})
    public void m() {
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.SingleShotTime})
    @Warmup(iterations = 3)
    public void m2() {
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.SingleShotTime})
    @Measurement(iterations = 3)
    public void m3() {
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.SingleShotTime})
    @Fork(value = 3)
    public void m4() {
    }

    /**
     * 仅限于IDE中运行
     * 命令行模式 则是 build 然后 java -jar 启动
     *
     * 1. 这是benchmark 启动的入口
     * 2. 这里同时还完成了JMH测试的一些配置工作
     * 3. 默认场景下，JMH会去找寻标注了@Benchmark的方法，可以通过include和exclude两个方法来完成包含以及排除的语义
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                // 包含语义
                // 可以用方法名，也可以用XXX.class.getSimpleName()
                .include("**.JMHSamples")
                // 排除语义
                .exclude("Pref")
                // 预热10轮
                .warmupIterations(10)
                // 代表正式计量测试做10轮，
                // 而每次都是先执行完预热再执行正式计量，
                // 内容都是调用标注了@Benchmark的代码。
                .measurementIterations(10)
                //  forks(3)指的是做3轮测试，
                // 因为一次测试无法有效的代表结果，
                // 所以通过3轮测试较为全面的测试，
                // 而每一轮都是先预热，再正式计量。
                .forks(3)
                .output("E:/Benchmark.log")
                // JMH支持以下5种格式的结果：
                // TEXT 导出文本文件。
                // CSV  导出csv格式文件。
                // SCSV 导出scsv等格式的文件。
                // JSON 导出成json文件。
                // LATEX 导出到latex，一种基于ΤΕΧ的排版系统。
                .resultFormat(ResultFormatType.JSON)
                .build();

        new Runner(opt).run();
    }
}

