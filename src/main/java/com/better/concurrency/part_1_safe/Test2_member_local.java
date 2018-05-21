package com.better.concurrency.part_1_safe;

import com.better.Utils;
import com.better.concurrency.anno.NotThreadSafe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 成员变量，访问没有的线程安全性问题
 */
public class Test2_member_local {

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

    @NotThreadSafe
    static class UnsafeSequence {
        private int value;

        public int getNext() {   // 需要加入同步 sync，这表示内置锁
            return value++;
        }
    }
}
