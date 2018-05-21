package com.better.concurrency.part_1_safe;

import com.better.Utils;
import com.better.concurrency.anno.NotThreadSafe;
import com.better.concurrency.anno.ThreadSafe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 多个成员变量，使用现有的线程安全类，如：AtomicInteger
 */
public class Test2_multi_member_local3 {

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
    private static class UnsafeSequence {
        // 虽然 AtomicInteger 与 AtomicReference 都是线程安全类，
        // 但不变性涉及到多个变量时，各个变量之间并不是独立的，即各个变量的值会对其他变量的值产生约束；
        // 所以这里也是不线程线程安全的，
        // 比较好懂的说法是：（可以认为这个2个线程安全的变量他们的锁不是同一个，所以会造成问题）
        // 尽管对set方法的每次操作但是原子的，都无法同时更新value与valueRef2个变量；
        private AtomicInteger value = new AtomicInteger(0);                            // 使用 AtomicInteger
        private AtomicReference<Integer> valueRef = new AtomicReference(0);        // 使用 AtomicReference

        public int getNext() {      // 需加入同步代码库解决问题
            /*
             // 变量相互影响，不安全，也就是需要在更新某一个变量时，需要在一个`原子操作上`对其他变量同时进行更新；*/
            valueRef.set(value.incrementAndGet());
            return valueRef.get();
        }
    }
}
