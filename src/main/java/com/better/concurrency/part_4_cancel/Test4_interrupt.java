package com.better.concurrency.part_4_cancel;

import com.better.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test4_interrupt {

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

        // 内部线程中断
        class RethrowableTask implements Runnable {
            private volatile Throwable t;   // 2个线程中调用，使用 volatile
            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() throws Exception {
                if(t != null) {
                    Utils.println("抛出异常");      // 再次抛出
                    throw new Exception("抛出异常。。。");
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();

        executor.schedule(new Runnable() {
            @Override
            public void run() {
                taskThread.interrupt();     // 中断
            }
        }, timeout, unit);
        taskThread.join(unit.toMillis(timeout));  // 等待，无法断定线程是正常退出，还是join出问题
        try {
            task.rethrow();
        } catch (Exception e) {
            Utils.println("接收到中断信息");
        }
    }
}
