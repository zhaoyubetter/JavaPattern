package com.better.concurrency.part_4_cancel;

import com.better.Utils;

import java.util.concurrent.*;

/**
 * 通过future来中断
 */
public class Test5_interrupt_future {
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

    public static void main(String... a) throws InterruptedException {

        timedRun(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    Utils.println("running....");

                }
            }
        }, 2, TimeUnit.SECONDS);        // 2 秒后中断
    }

    public static void timedRun(Runnable r,
                                long timeout, TimeUnit unit) throws InterruptedException {

        Future<?> future = executor.submit(r);
        try {
            future.get(timeout, unit);
        } catch (TimeoutException e) {

        } catch (ExecutionException e) {
            Utils.println("重新抛出异常");
        } finally {
            future.cancel(true);    // 任务正在运行，中断他，里面调用 intercerupt 方法
        }
    }
}
