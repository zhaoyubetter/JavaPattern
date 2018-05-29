package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁使用
 */
public class Test6_CountDownLatch_1 {
    public static void main(String... aa) throws InterruptedException {
        TaskTimeRecord record = new TaskTimeRecord();
        long costTime = record.timeTasks(5, new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Utils.println("current Thread: " + Thread.currentThread().getName() + " do working...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Utils.println("cost : " + costTime);
    }

    static class TaskTimeRecord {
        public long timeTasks(final int nThreads, final Runnable task) throws InterruptedException {
            // 开始门
            final CountDownLatch startGate = new CountDownLatch(1);
            // 结束门
            final CountDownLatch endGate = new CountDownLatch(nThreads);

            // 启动多个线程
            for (int i = 0; i < nThreads; i++) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            startGate.await();  // 等待
                            task.run();
                        } catch (InterruptedException e) {
                        } finally {
                            endGate.countDown();
                        }
                    }
                }.start();
            }

            long startTime = System.currentTimeMillis();
            startGate.countDown();      // 开门
            endGate.await();
            return System.currentTimeMillis() - startTime;
        }
    }
}
