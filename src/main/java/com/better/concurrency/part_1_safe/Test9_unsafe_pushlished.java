package com.better.concurrency.part_1_safe;

import com.better.Utils;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 不正确的发布
 */
public class Test9_unsafe_pushlished {


    static Holder holder = null;

    public static void main(String... args) throws Exception {

        final int value = 50;

        // 线程中初始化
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    holder = new Holder(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final int next = new Random().nextInt(100);
                    try {
                        Thread.sleep(next);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                    holder.assertSantiy(value);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final int next = new Random().nextInt(100);
                    try {
                        Thread.sleep(next);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                    holder.assertSantiy(value);

                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final int next = new Random().nextInt(100);
                    try {
                        Thread.sleep(next);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                    holder.assertSantiy(value);
                }
            }
        });


        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(10000);
    }

    /**
     * 对象尚未创建完毕，就发布了
     */
    static class Holder {
        private int n;

        public Holder(final int n) throws InterruptedException {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Holder.this.n = n;
                }
            }).start();
        }

        public void assertSantiy(int value) {
            if (n != value) {
                Utils.println("同步失败, n: " + n + ", pass value: " + value);
                System.exit(0);
            }
        }
    }
}
