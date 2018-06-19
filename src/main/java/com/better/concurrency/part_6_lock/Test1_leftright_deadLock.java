package com.better.concurrency.part_6_lock;

import com.better.Utils;

import java.util.Random;

/**
 * 不同的顺序请求锁，容易出现死锁
 * 2个线程操作交错执行
 */
public class Test1_leftright_deadLock {
    public static void main(String... a) {
        final LeftRightDeadLock lock = new LeftRightDeadLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.leftRight();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Thread.sleep(3000);       顺序调用时，没有循环依赖性问题
                    lock.rightLeft();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static class LeftRightDeadLock {
        private final Object left = new Object();
        private final Object right = new Object();

        public void leftRight() throws InterruptedException {
            synchronized (left) {
                Utils.println("获取left锁---");
                Thread.sleep(50);
                synchronized (right) {
                    Thread.sleep(new Random().nextInt(300));
                    Utils.println("left ----> right");
                }
            }
        }

        public void rightLeft() throws InterruptedException {
            synchronized (right) {
                Utils.println("获取right锁---");
                Thread.sleep(20);
                synchronized (left) {
                    Thread.sleep(new Random().nextInt(300));
                    Utils.println("right ----> left");
                }
            }
        }
    }
}
