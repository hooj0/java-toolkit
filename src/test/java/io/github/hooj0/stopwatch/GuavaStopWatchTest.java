package io.github.hooj0.stopwatch;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * guava 秒表测试
 *
 * @author hoojo
 * @version 1.0
 * @date 2022/01/20 14:58:28
 */
public class GuavaStopWatchTest {
    /*
        方法类型	                方法描述
        static Stopwatch	createStarted()         创建启动一个新的stopwatch对象，用的是System.nanoTime()作为时间资源。
        static Stopwatch	createStarted(Ticker ticker)        创建启动一个新的stopwatch对象，用的是特定的时间资源。
        static Stopwatch	createUnstarted()       创建（但不启动）一个新的stopwatch对象，用的是System.nanoTime()作为时间资源。
        static Stopwatch	createUnstarted(Ticker ticker)      创建（但不启动）一个新的stopwatch对象，用的是特定的时间资源。
        Duration	        elapsed()               返回将此秒表上显示的当前经过时间作为持续时间.
        long	            elapsed(TimeUnit desiredUnit)       用特定的格式返回这个stopwatch经过的时间.
        boolean	            isRunning()     如果start方法被调用。stop方法还没有调用。返回真.
        Stopwatch	        reset()         把stopwatch经过的时间设置为零，状态设置为停止.
        Stopwatch	        start()         启动 stopwatch.
        Stopwatch	        stop()          停止stopwatch，读取的话将会返回经历过的时间.
        String	            toString()      返回字符串形式的elapsed time.
    */
    @Test
    public void test1() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        this.something();
        stopwatch.stop();

        Duration duration = stopwatch.elapsed();
        System.out.println("逻辑代码运行耗时：" + duration.toMillis());

        stopwatch.start();
        this.something();
        stopwatch.stop();

        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("逻辑代码运行耗时：" + nanos);

        stopwatch.reset();
        stopwatch.start();
        this.something();

        nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("逻辑代码运行耗时：" + nanos);
        System.out.println(stopwatch);
    }

    @Test
    public void test2() {
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        stopwatch.start();
        this.something();
        System.out.println(stopwatch.stop());

        this.something(); // 忽略，不计时间

        stopwatch.start();
        this.something();
        System.out.println(stopwatch.stop());

        stopwatch.reset();
        stopwatch.start();
        this.something();
        System.out.println(stopwatch.stop());
    }

    @Test
    public void test3() {
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        stopwatch.start();
        this.something();
        System.out.println(stopwatch.elapsed().toMillis());

        this.something();
        System.out.println(stopwatch.elapsed().toMillis());

        stopwatch.reset();
        stopwatch.start();
        this.something();
        System.out.println(stopwatch.stop().elapsed(TimeUnit.MICROSECONDS));
    }

    private void something() {
        IntStream.rangeClosed(1, 10).forEach((i) -> {
            System.out.print(">>> " + i);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException ignored) {
            }
        });
        System.out.println();
    }
}
