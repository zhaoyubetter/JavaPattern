package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.*;
import java.util.concurrent.*;

/**
 * 缓存设计3，利用ConcurrentHashMap 结合 Future
 * 问题：同样会出现问题，跟cache_2类似，原因在于复合运算时没有同步
 */
public class Test14_cache_3 {
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

        private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
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
            Future<V> future = cache.get(arg);
            // 这里的 if 不是同步的
            if(future == null) {
                final Callable<V> callable = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(arg);
                    }
                };
                future = new FutureTask<>(callable);
                cache.put(arg,future);
                ((FutureTask<V>) future).run();
            }

            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
