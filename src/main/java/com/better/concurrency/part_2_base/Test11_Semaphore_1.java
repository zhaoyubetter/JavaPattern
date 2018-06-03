package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 用来控制同时访问某个特定资源的操作数量，或者执行某个指定操作的数量；
 * 控制流量
 *
 * 有界阻塞容器
 */
public class Test11_Semaphore_1 {

    public static void main(String... args) {
        BoundedHashSet test = new BoundedHashSet(5);        // 允许5个线程同时进入
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int num = new Random().nextInt(100);
                        test.add(num);
                        Thread.sleep(num);
                        test.remove(num);
                    } catch (InterruptedException e) {
                    }
                }
            }).start();
        }
    }

    static class BoundedHashSet<T> {
        private final Set<T> set;
        private final Semaphore sem;
        private final int max_cout;

        public BoundedHashSet(int bound) {
            this.set = Collections.synchronizedSet(new HashSet<>());
            this.sem = new Semaphore(bound);
            max_cout = bound;
        }

        public boolean add(T o) throws InterruptedException {
            // 1.获取许可
            sem.acquire();
            boolean wasAdded = false;
            try {
                wasAdded = set.add(o);
                Thread.sleep(new Random().nextInt(300));
                return wasAdded;
            } finally {
                Utils.println("thread: " + Thread.currentThread().getName() + "进入，当前有" + (max_cout-sem.availablePermits()) + "个并发");
                if (!wasAdded) {
                    // 2.失败，释放许可
                    sem.release();
                }
            }
        }

        public boolean remove(Object o) throws InterruptedException {
            boolean wasRemove = set.remove(o);
            if (wasRemove) {
                sem.release();  // 释放许可
                Utils.println("thread: " + Thread.currentThread().getName() + "已离开，当前有" + (max_cout-sem.availablePermits()) + "个并发");
            }
            return wasRemove;
        }
    }
}
