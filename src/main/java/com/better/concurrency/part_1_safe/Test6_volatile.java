package com.better.concurrency.part_1_safe;

import com.better.Utils;
import com.better.concurrency.anno.GuardedBy;
import com.better.concurrency.anno.NotThreadSafe;
import com.better.concurrency.anno.ThreadSafe;

import java.util.Random;

/**
 * volatile
 * TODO: // 例子不合适
 */
@NotThreadSafe
public class Test6_volatile {

    private static volatile int num = 0;
    private static volatile boolean stop = false;

    public static void main(String[] args) throws Exception {
        final Test6_volatile data = new Test6_volatile();


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(new Random().nextInt(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final int next = new Random().nextInt(100);
                    num = next;
                    Utils.println(String.format("Thread name:%s, set: %s", Thread.currentThread(), next));
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(new Random().nextInt(20));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final int next = new Random().nextInt(100);
                    num = next;
                    Utils.println(String.format("Thread name:%s, set: %s ", Thread.currentThread(), next));
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(new Random().nextInt(20));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final int next = new Random().nextInt(100);
                    num = next;
                    Utils.println(String.format("Thread name:%s, ====> set: %s ", Thread.currentThread(), next));
                    if(next == 90) {
                        stop = true;
                    }
                }
            }
        });


        t1.start();
        t2.start();
        t3.start();

        data.test();

        Thread.sleep(10000);
    }

    private void test() throws InterruptedException {
        Thread.sleep(new Random().nextInt(10));
        while (!stop) {
            Utils.println("num is :" + num);
        }

        Utils.println("num is :" + num);
        System.exit(0);
    }
}
