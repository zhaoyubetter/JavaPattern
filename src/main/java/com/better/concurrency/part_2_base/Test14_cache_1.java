package com.better.concurrency.part_2_base;

import com.better.Utils;
import com.better.concurrency.anno.GuardedBy;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

/**
 * 缓存设计1，整个方法同步的做法;
 * 问题：
 * 1. 整个方法，同步，虽然没问题，但有效率问题
 */
public class Test14_cache_1 {
    public static void main(String[] aar) throws InterruptedException, BrokenBarrierException {
        final long startTime = System.currentTimeMillis();
        final Memoizer memoizer = new Memoizer(new ExpensiveFunction());

        for (int i = 0; i < 20; i++) {
            Thread a = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 50; j < 70; j++) {
                        try {
                            Utils.println("thread: " + Thread.currentThread().getName() + ", value: " + memoizer.compute(j + ""));
                        } catch (InterruptedException e) {
                        }
                    }
                }
            });
            a.start();
        }


        Thread.sleep(5000);
        Utils.println("cost: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static interface Computable<A, V> {
        V compute(A a) throws InterruptedException;
    }

    private static class ExpensiveFunction implements Computable<String, Integer> {
        @Override
        public Integer compute(String s) throws InterruptedException {
            Thread.sleep(new Random().nextInt(500));
            Utils.println(">>>>" + Thread.currentThread().getName() + " 计算： " + s);
            return new Integer(s);
        }
    }

    private static class Memoizer<A, V> implements Computable<A, V> {

        @GuardedBy("this")
        private final Map<A, V> cache = new HashMap<>();
        private final Computable<A, V> c;

        public Memoizer(Computable<A, V> c) {
            this.c = c;
        }

        /**
         * 方法整体同步，每次只能一个线程访问此方法
         *
         * @param arg
         * @return
         * @throws InterruptedException
         */
        @Override
        public synchronized V compute(A arg) throws InterruptedException {
            V v = cache.get(arg);
            if (v == null) {
                v = c.compute(arg);
                cache.put(arg, v);
            }
            return v;
        }
    }

}
