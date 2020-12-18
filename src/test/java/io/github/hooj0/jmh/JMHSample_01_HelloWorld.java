package io.github.hooj0.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * hello world
 *
 * @author hoojo
 * @version 1.0
 * @date 2022/2/17 0017 10:53
 */
public class JMHSample_01_HelloWorld {

    @Benchmark
    public void sayHello() {
        //System.out.println("hello world!");
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHSample_01_HelloWorld.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
