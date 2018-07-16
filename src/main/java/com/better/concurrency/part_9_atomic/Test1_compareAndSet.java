package com.better.concurrency.part_9_atomic;

import com.better.Utils;
import com.better.concurrency.anno.ThreadSafe;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 比较并交换
 * CAS
 */
public class Test1_compareAndSet {
    public static void main(String[] ar) {
        CastCounter counter = new CastCounter();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    counter.increment();
                    Utils.println(Thread.currentThread().getName() + " ---> " + counter.getValue());
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    counter.increment();
                    Utils.println(Thread.currentThread().getName() + " ---> " + counter.getValue());
                }
            }).start();
        }
    }

    @ThreadSafe
    private static class SimulatedCAS {
        private int value;

        public synchronized int get() {
            return value;
        }

        public synchronized int compareAndSwap(int exceptedValue, int newValue) {
            int old = value;
            if (old == exceptedValue) {
                Utils.println(Thread.currentThread().getName() + " change to " + newValue);
                value = newValue;
            } else {
                Utils.println(Thread.currentThread().getName() + " " + String.format("value:%s , A:%s, B:%s, return:%s, give up change....", value, exceptedValue, newValue, old));        // 不相等 increment 方法，继续循环
            }
            return old;     // 返回old
        }

        public synchronized boolean compareAndSet(int exceptedValue, int newValue) {
            return (exceptedValue == compareAndSwap(exceptedValue, newValue));
        }
    }

    /**
     * 计数器，可多个线程同时访问
     */
    @ThreadSafe
    private static class CastCounter {
        private final SimulatedCAS cas = new SimulatedCAS();

        public int getValue() {
            return cas.get();
        }

        /**
         * threadA value 等于 1，此时，threadB修改value成2；
         * 此时CAS不成立threadA 将继续循环；取出新的value，并判断，如果相等，继续循环。。。否则放弃，并返回老value，因为其他线程帮你完成了任务；
         * @return
         */
        public int increment() {
            int v;
            do {
                v = cas.get();
                try {
                    Thread.sleep(new Random().nextInt(200));
                } catch (InterruptedException e) {}
            } while (v != cas.compareAndSwap(v, v + 1));
            return v;
        }
    }

}
