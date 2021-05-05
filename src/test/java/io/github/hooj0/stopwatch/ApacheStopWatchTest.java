package io.github.hooj0.stopwatch;

import lombok.SneakyThrows;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * apache秒表测试
 *
 * @author hoojo
 * @version 1.0
 * @date 2022/01/20 16:25:34
 */
public class ApacheStopWatchTest {
    /*
        start();            //开始计时
        split();            //设置split点
        getSplitTime();     //获取从start 到 最后一次split的时间
        reset();            //重置计时
        suspend();          //暂停计时, 直到调用resume()后才恢复计时
        resume();           //恢复计时
        stop();             //停止计时
        getTime();          //统计从start到现在的计时
    */

    @Test
    @SneakyThrows
    public void test() {
        StopWatch stopWatch = StopWatch.createStarted();
        System.out.println("Time: " + stopWatch.getTime());
        System.out.println("StartTime: " + stopWatch.getStartTime());
        System.out.println("NanoTime: " + stopWatch.getNanoTime());
        System.out.println("-------------------------------------------");

        this.something();
        System.out.println("1 Time: " + stopWatch.getTime());

        this.something();
        System.out.println("2 Time: " + stopWatch.getTime());

        this.something();
        System.out.println("3 Time: " + stopWatch.getTime());

        stopWatch.stop();
        System.out.println("-------------------------------------------");
        System.out.println("Time: " + stopWatch.getTime());
        System.out.println("NanoTime: " + stopWatch.getNanoTime());
        System.out.println("StartTime: " + stopWatch.getStartTime());
    }

    @Test
    @SneakyThrows
    public void test1() {
        StopWatch stopWatch = new StopWatch("test");

        stopWatch.start();
        this.something();
        System.out.println("Time: " + stopWatch.getTime());

        this.something();
        System.out.println("Time: " + stopWatch.getTime());
        System.out.println("-------------------------------------------");

        stopWatch.split();
        System.out.println("1 SplitTime: " + stopWatch.getSplitTime());
        System.out.println("1 Time: " + stopWatch.getTime());

        this.something();
        stopWatch.split();
        System.out.println("2 SplitTime: " + stopWatch.getSplitTime());
        System.out.println("2 Time: " + stopWatch.getTime());
        System.out.println("-------------------------------------------");

        this.something();
        System.out.println("3 before SplitTime: " + stopWatch.getSplitTime());
        System.out.println("3 Time: " + stopWatch.getTime());
        System.out.println("formatTime: " + stopWatch.formatTime());
        System.out.println("-------------------------------------------");

        stopWatch.suspend(); //暂停
        this.something(); // 不计入
        System.out.println("suspend Time: " + stopWatch.getTime(TimeUnit.MILLISECONDS));

        stopWatch.resume(); //上面suspend，这里要想重新统计，需要恢复一下
        System.out.println("resume Time: " + stopWatch.getTime(TimeUnit.MILLISECONDS));
        this.something();
        System.out.println("resume Time: " + stopWatch.getTime(TimeUnit.MILLISECONDS));

        stopWatch.stop();
        System.out.println("-------------------------------------------");
        System.out.println("Time: " + stopWatch.getTime());
        System.out.println(stopWatch.toString());
    }

    @Test
    @SneakyThrows
    public void test2() {
        StopWatch stopWatch = new StopWatch("test");

        stopWatch.start();
        System.out.println("StartTime: " + stopWatch.getStartTime());
        this.something();
        stopWatch.stop();
        System.out.println("Time: " + stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        System.out.println("StartTime: " + stopWatch.getStartTime());
        this.something();
        stopWatch.stop();
        System.out.println("Time: " + stopWatch.getTime());
        System.out.println("NanoTime: " + stopWatch.getNanoTime());
    }

    @Test
    @SneakyThrows
    public void test3() {
        StopWatch stopWatch = StopWatch.createStarted();

        this.something(); // 100
        stopWatch.split();
        System.out.println("1 SplitTime: " + stopWatch.getSplitTime());
        System.out.println("1 Time: " + stopWatch.getTime());

        this.sleep(); // 110
        System.out.println("2 SplitTime latest: " + stopWatch.getSplitTime());
        System.out.println("*** sleep Time: " + stopWatch.getTime());
        System.out.println("--------------------------------------------");

        stopWatch.unsplit();
        this.something(); // 210
        stopWatch.split();
        System.out.println("3 SplitTime: " + stopWatch.getSplitTime());
        System.out.println("3 Time: " + stopWatch.getTime());

        this.sleep(); // 220
        System.out.println("*** sleep Time: " + stopWatch.getTime());

        System.out.println("--------------------------------------------");
        stopWatch.unsplit();
        this.something(); // 320
        stopWatch.split();
        System.out.println("4 SplitTime: " + stopWatch.getSplitTime());
        System.out.println("4 Time: " + stopWatch.getTime());

        System.out.println("formatTime: " + stopWatch.formatTime());
        System.out.println("formatSplitTime: " + stopWatch.formatSplitTime());
        System.out.println("toSplitString: " + stopWatch.toSplitString());
        System.out.println("SplitNanoTime: " + stopWatch.getSplitNanoTime());

        stopWatch.stop();
        System.out.println("-------------------------------------------");
        System.out.println("Time: " + stopWatch.getTime());
    }

    @Test
    @SneakyThrows
    public void test4() {
        // Create an instance of StopWatch and start the stopwatch.
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // Do some task and split the stopwatch time.
        this.something(); // 100
        stopWatch.split();
        System.out.println("Split 1: " + stopWatch.getSplitTime());

        // Suspend the stopwatch and resume the stopwatch.
        stopWatch.suspend();
        this.something(); // 100
        stopWatch.resume();

        // Do some task and split the stopwatch time.
        this.something(); // 200
        System.out.println("Split 2: " + stopWatch.getSplitTime());//0
        System.out.println("Split 2: " + stopWatch.getTime());

        // Do some task and split the stopwatch time.
        this.something(); // 300
        stopWatch.split();
        System.out.println("Split 3: " + stopWatch.getSplitTime());

        // Do some task and split the stopwatch time.
        this.something(); // 400
        System.out.println("Split 4: " + stopWatch.getSplitTime());

        // Stop the stopwatch and the total execution time.
        stopWatch.stop();
        System.out.println("Time: " + stopWatch.getTime());
    }

    @Test
    @SneakyThrows
    public void test5() {
        StopWatch clock = new StopWatch();

        clock.start();
        this.doSomething("Task 1.");

        clock.split();
        System.out.println("Task-1 -> time split: " + clock.getSplitTime());
        System.out.println("Task-1 -> time total: " + clock.getTime());
        System.out.println("Task-2 -> time used: " + (clock.getTime() - 0));

        this.doSomething("Task 2.");
        System.out.println("Task-2 -> time pre split: " + clock.getSplitTime());
        System.out.println("Task-2 -> time total: " + clock.getTime());
        System.out.println("Task-2 -> time used: " + (clock.getTime() - clock.getSplitTime()));

        clock.split();
        this.doSomething("Task 3.");
        System.out.println("Task-3 -> time split: " + clock.getSplitTime());
        System.out.println("Task-3 -> time total: " + clock.getTime());
        System.out.println("Task-3 -> time used: " + (clock.getTime() - clock.getSplitTime()));

        this.doSomething("Task 4.");
        System.out.println("Task-4 -> time pre split: " + clock.getSplitTime());
        System.out.println("Task-4 -> time total: " + clock.getTime());
        System.out.println("Task-3&4 -> time used: " + (clock.getTime() - clock.getSplitTime()));

        this.doSomething("Task 5.");
        System.out.println("Task-5 -> time pre split: " + clock.getSplitTime());
        System.out.println("Task-5 -> time total: " + clock.getTime());
        System.out.println("Task-3&4&5 -> time used: " + (clock.getTime() - clock.getSplitTime()));
    }

    private void something() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    private void sleep() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(10);
    }

    private void doSomething(String task) throws InterruptedException {
        System.out.println(task + "-----------------");
        TimeUnit.MILLISECONDS.sleep(100);
        //TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(10, 100));
    }
}
