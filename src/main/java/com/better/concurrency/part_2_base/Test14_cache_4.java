package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.*;
import java.util.concurrent.*;

/**
 * 缓存设计4，完美实现
 */
public class Test14_cache_4 {
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
            if (future == null) {
                final Callable<V> callable = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> f = new FutureTask<>(callable);
                future = cache.putIfAbsent(arg, f);     // 原子方法 , 如果不存在（新的entry），那么会向map中添加该键值对，并返回null，存在返回上一次的value，区别于put方法
                if (future == null) {
                    future = f;
                    f.run();
                }
            }

            try {
                return future.get();
            } catch (CancellationException e) {     // future 取消了,则移除 futureTask
                cache.remove(arg, future);
            } catch (ExecutionException e) {
                cache.remove(arg, future);
                throw new RuntimeException(e);
            }

            return null;
        }
    }

}
