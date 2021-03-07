package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock
 * 乐观读 Optimistic
 */
public class Test6_StampedLock1 {
    @Test
    public void test() throws InterruptedException {
        final Point p = new Point();
        final Runnable t1 = () -> p.setXY(10, 10);
        final Runnable t2 = () -> {
            try {
                Utils.println(p.distanceFromOrigin());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        p.setXY(2, 2);
        final CountDownLatch latch = new CountDownLatch(1);
        final ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            executorService.submit(
                    () -> {
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (new Random().nextInt(10) > 5) {
                            t1.run();
                        } else {
                            t2.run();
                        }
                    }
            );
        }


        latch.countDown();
        Thread.sleep(1000);
    }

    static class Point {
        private int x, y;


        final StampedLock sl = new StampedLock();

        int distanceFromOrigin() throws InterruptedException {
            // 乐观读
            long stamp = sl.tryOptimisticRead();
            // 读入局部变量，读的过程数据可能被修改
            int curX = x;
            int curY = y;

            Thread.sleep(20);   // Current Thread have a rest ~

            // 判断执行读操作期间，是否存在写操作，如果存在，则sl.validate 返回 false
            // 存在写操作，则重新赋值
            if (!sl.validate(stamp)) {
                // 升级为悲观读锁
                stamp = sl.readLock();
                try {
                    curX = x;
                    curY = y;
                } finally {
                    // 释放悲观读锁
                    sl.unlockRead(stamp);
                }
            } else {
                Utils.println("Another Thread was writing value during " + Thread.currentThread().getName() + " reading...");
            }

            return (int) Math.sqrt(curX * curX + curY * curY);
        }

        void setXY(int x, int y) {
            long stamp = sl.writeLock();
            try {
                this.x = x;
                this.y = y;
            } finally {
                sl.unlockWrite(stamp);
            }
        }
    }
}
