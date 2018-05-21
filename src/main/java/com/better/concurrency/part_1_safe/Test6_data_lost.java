package com.better.concurrency.part_1_safe;

import com.better.Utils;
import com.better.concurrency.anno.GuardedBy;
import com.better.concurrency.anno.NotThreadSafe;
import com.better.concurrency.anno.ThreadSafe;

import java.util.Random;

/**
 * 数据失效问题, 如何更改，需要为方法，加入 sync，读取方法也需要加入；
 * <p>
 * 因为：没有同步的情况下，读取一个值，有可能获取的是失效的值
 *
 *
 * TODO:// 数据失效问题 ===》 不好模拟环境，此例子有问题；
 */
@NotThreadSafe
public class Test6_data_lost {

    private static volatile int last = -1;

    public static void main(String[] args) throws Exception {
        final Test6_data_lost data = new Test6_data_lost();
//        final Test6_data_lost2 data = new Test6_data_lost2();

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
                    data.setValue(next);
                    last = next;
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
                    data.setValue(next);
                    last = next;
                    Utils.println(String.format("Thread name:%s, set: %s ", Thread.currentThread(), next));
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    try {
//                        Thread.sleep(new Random().nextInt(20));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    Utils.println(String.format("Thread name:%s, >>>>>> get: %s ", Thread.currentThread(), data.getValue()));
                    try {
                        Thread.sleep(new Random().nextInt(20));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        t1.start();
        t2.start();
        t3.start();


        Thread.sleep(10000);
    }

    // 给个初始值，用来标识
    private int value = -1;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    // ========

    @ThreadSafe
    private static class Test6_data_lost2 {
        // 给个初始值，用来标识
        @GuardedBy("this")
        private int value = -1;

        public synchronized int getValue() {
            return value;
        }

        public synchronized void setValue(int value) {
            this.value = value;
        }
    }
}
