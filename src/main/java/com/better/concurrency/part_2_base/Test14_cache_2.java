package com.better.concurrency.part_2_base;

import com.better.Utils;
import com.better.concurrency.anno.GuardedBy;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存设计2，利用ConcurrentHashMap 替换HashMap
 * 问题：
 * 1. 虽然ConcurrentHashMap访问与写入是同步的，
 * 但是线程1正在计算A值（耗时），但其他线程，却不知道线程1在计算A值，所有会造成重复计算；
 */
public class Test14_cache_2 {
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


        Thread.sleep(10000);
        Utils.println("cost: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static interface Computable<A, V> {
        V compute(A a) throws InterruptedException;
    }

    private static class ExpensiveFunction implements Computable<String, Integer> {

        private List<String> list = Collections.synchronizedList(new ArrayList<>(50));

        @Override
        public Integer compute(String s) throws InterruptedException {
            synchronized (list) {
                if (list.contains(s)) {
                    Utils.println("----- 重复计算： " + s + ", thread: " + Thread.currentThread().getName());
                    System.exit(0);
                }
                list.add(s);
            }

            Thread.sleep(new Random().nextInt(500));
            Utils.println(">>>>" + Thread.currentThread().getName() + " 计算： " + s);

            return new Integer(s);
        }
    }

    private static class Memoizer<A, V> implements Computable<A, V> {

        private final Map<A, V> cache = new ConcurrentHashMap<>();
        private final Computable<A, V> c;

        public Memoizer(Computable<A, V> c) {
            this.c = c;
        }

        /**
         * 利用ConcurrentHashMap 替换HashMap
         *
         * @param arg
         * @return
         * @throws InterruptedException
         */
        @Override
        public V compute(A arg) throws InterruptedException {
            V v = cache.get(arg);
            if (v == null) {
                v = c.compute(arg);
                cache.put(arg, v);
            }
            return v;
        }
    }

}
