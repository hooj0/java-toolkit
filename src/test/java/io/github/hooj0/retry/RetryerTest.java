package io.github.hooj0.retry;

import com.github.rholder.retry.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 重试框架，重试执行一段业务
 *
 * @author hoojo
 * @version 1.0
 * @date 2022/01/20 14:42:00
 */
public class RetryerTest {

    private Retryer<Boolean> retryer;

    @Before
    public void setup() {
        retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Objects::isNull)
                .retryIfException()
                .retryIfRuntimeException()
                .retryIfExceptionOfType(NullPointerException.class)
                // 执行多久后停止（在未到停止的时间节点前，如果失败，会一致重试）
                .withStopStrategy(StopStrategies.stopAfterDelay(10L, TimeUnit.SECONDS))
                // 重试间隔设置
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
                .build();
    }

    @Test
    public void retryCall() {
        try {
            this.retryer.call(() -> {
                System.out.println(System.currentTimeMillis());
                return null;
            });
        } catch (ExecutionException | RetryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void retryMoreCall() {
        try {
            this.retryer.call(() -> {
                System.out.println(System.currentTimeMillis());
                return null;
            });
        } catch (ExecutionException | RetryException e) {
            e.printStackTrace();
        }

        try {
            this.retryer.call(() -> {
                System.out.println("h!~");
                return null;
            });
        } catch (ExecutionException | RetryException e) {
            e.printStackTrace();
        }
    }
}
