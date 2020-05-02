package com.better.concurrency.part_6_lock;

import com.better.Utils;

import java.util.Random;

/**
 * 不同的顺序请求锁，容易出现死锁
 * 2个线程操作交错执行
 */
public class Test1_leftright_deadLock2 {
    public static void main(String... a) throws InterruptedException {
        final LeftRightDeadLock lock = new LeftRightDeadLock();

        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.leftRight();
            }
        });

        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.rightLeft();
            }
        });

        t1.start();
        t2.start();
        Thread.sleep(500);
        t1.interrupt();     // 调用中断，死锁代码也不能响应中断了
    }

    private static class LeftRightDeadLock {
        private final Object left = new Object();
        private final Object right = new Object();

        public void leftRight() {
            synchronized (left) {
                Utils.println("获取left锁---");
                try {
                    Thread.sleep(50);
                    synchronized (right) {
                        Thread.sleep(new Random().nextInt(300));
                        Utils.println("left ----> right");
                    }
                } catch (InterruptedException e) {
                    Utils.print("leftRight -> 捕获中断...");
//                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }

        public void rightLeft() {
            synchronized (right) {
                Utils.println("获取right锁---");
                try {
                    Thread.sleep(20);
                    synchronized (left) {
                        Thread.sleep(new Random().nextInt(300));
                        Utils.println("right ----> left");
                    }
                } catch (InterruptedException e) {
                    Utils.print("rightLeft -> 捕获中断...");
                    e.printStackTrace();
                }
            }
        }
    }
}
