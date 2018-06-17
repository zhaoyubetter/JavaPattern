package com.better.concurrency.part_4_cancel;

import com.better.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 外界中断
 */
public class Test3_interrupt_out {

    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

    public static void main(String... a) throws InterruptedException {

        timedRun(new Runnable() {
            @Override
            public void run() {
                // 没有中断，一直运行
                while(!Thread.currentThread().isInterrupted()) {
                    Utils.println("running....");
                }

                Utils.println("....cancel");
            }
        }, 2, TimeUnit.SECONDS);        // 2 秒后中断
    }

    public static void timedRun(Runnable r,
                                long timeout, TimeUnit unit) {
        final Thread taskThread = Thread.currentThread();
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                taskThread.interrupt();     // 中断
            }
        }, timeout, unit);
        r.run();
    }
}
