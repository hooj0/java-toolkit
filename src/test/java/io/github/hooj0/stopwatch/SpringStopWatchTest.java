package io.github.hooj0.stopwatch;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * spring 秒表测试
 *
 * @author hoojo
 * @version 1.0
 * @date 2022/01/20 15:51:13
 */
public class SpringStopWatchTest {

    @Test
    public void test() {
        StopWatch stopWatch = new StopWatch("test");

        stopWatch.start("task");
        something();
        stopWatch.stop();

        System.out.println("LastTaskName: " + stopWatch.getLastTaskName());
        System.out.println("LastTaskTimeMillis: " + stopWatch.getLastTaskTimeMillis());
        System.out.println("LastTaskInfo: " + ToStringBuilder.reflectionToString(stopWatch.getLastTaskInfo()));

        something(); // 忽略 不计时间

        stopWatch.start("task 1");
        something();
        stopWatch.stop();

        System.out.println("LastTaskName: " + stopWatch.getLastTaskName());
        System.out.println("LastTaskTimeMillis: " + stopWatch.getLastTaskTimeMillis());
        System.out.println("LastTaskInfo: " + ToStringBuilder.reflectionToString(stopWatch.getLastTaskInfo()));

        System.out.println(stopWatch.prettyPrint());
        System.out.println("shortSummary: " + stopWatch.shortSummary());
        System.out.println("TaskCount: " + stopWatch.getTaskCount());
        System.out.println("TotalTimeMillis: " + stopWatch.getTotalTimeMillis());
        System.out.println("TotalTimeSeconds: " + stopWatch.getTotalTimeSeconds());
    }

    private void something() {
        IntStream.rangeClosed(1, 10).forEach((i) -> {
            System.out.print(">>> " + i);
            try {
                TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(10, 100));
            } catch (InterruptedException ignored) {
            }
        });
        System.out.println();
    }
}
/*
>>> 1>>> 2>>> 3>>> 4>>> 5>>> 6>>> 7>>> 8>>> 9>>> 10
LastTaskName: task
LastTaskTimeMillis: 1074
LastTaskInfo: org.springframework.util.StopWatch$TaskInfo@8807e25[taskName=task,timeNanos=1074142392]
>>> 1>>> 2>>> 3>>> 4>>> 5>>> 6>>> 7>>> 8>>> 9>>> 10
>>> 1>>> 2>>> 3>>> 4>>> 5>>> 6>>> 7>>> 8>>> 9>>> 10
LastTaskName: task 1
LastTaskTimeMillis: 762
LastTaskInfo: org.springframework.util.StopWatch$TaskInfo@51016012[taskName=task 1,timeNanos=762109884]
StopWatch 'test': running time = 1836252276 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
1074142392  058%  task
762109884  042%  task 1

shortSummary: StopWatch 'test': running time = 1836252276 ns
TaskCount: 2
TotalTimeMillis: 1836
TotalTimeSeconds: 1.836252276
*/