package com.better.concurrency.part_1_safe;

import com.better.Utils;
import com.better.concurrency.anno.ThreadSafe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 可重入锁
 */
public class Test3_reentry {
    private static void check(Set<Integer> set, int num) {
        if (set.contains(num)) {
            Utils.println("有重复数据：" + num);
            System.exit(0);
        } else {
            set.add(num);
        }
    }

    public static void main(String... args) throws Exception {

        final UnsafeSequence seq = new UnsafeSequence();
        final Set<Integer> set = Collections.synchronizedSet(new HashSet<>(1000));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(new Random().nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int next = seq.getNext();
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                    check(set, next);
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
                    final int next = seq.getNext();
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                    check(set, next);
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(new Random().nextInt(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int next = seq.getNext();
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                    check(set, next);
                }
            }
        });


        t1.start();
        t2.start();
        t3.start();


        Thread.sleep(10000);
    }


    @ThreadSafe
    private static class UnsafeSequence {
        private AtomicInteger value = new AtomicInteger(0);                            // 使用 AtomicInteger
        private AtomicReference<Integer> valueRef = new AtomicReference(0);        // 使用 AtomicReference

        /**
         * 使用Java提供的内置锁机制来支持原子性：sync，同步代码块
         *
         * @return
         */
        public synchronized int getNext() {
            valueRef.set(value.incrementAndGet());
            try {
                Thread.sleep(new Random().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return next();
        }

        private synchronized int next() {       // 可重入锁
            try {
                Thread.sleep(new Random().nextInt(20));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return valueRef.get();
        }
    }
}
