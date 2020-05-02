package com.better.concurrency.two;

import com.better.Utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 */
public class Test2Lock {
    public static void main(String[] args) {
        // 都支持重入；

        final X x = new X();
        x.addOne();
        Utils.println(x.get());

        final Y y = new Y();
        y.addOne();
        Utils.println(y.get());
    }

    static class X {
        private final Lock rtl = new ReentrantLock();
        int value;

        public int get() {
            // 获取锁
            rtl.lock();
            try {
                return value;
            } finally {
                // 保证锁能释放
                rtl.unlock();
            }
        }

        public void addOne() {
            // 获取锁
            rtl.lock();
            try {
                value = 1 + get();
            } finally {
                // 保证锁能释放
                rtl.unlock();
            }
        }
    }

    static class Y {
        private final Object lock = new Object();
        int value;

        public void addOne() {
            synchronized (lock) {
                value = 1 + get();
            }
        }

        public int get() {
            synchronized (lock) {
                return value;
            }
        }
    }
}
