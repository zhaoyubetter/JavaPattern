package com.better.concurrency.two;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁分离
 */
public class Test5_ReadWriteLock {
    @Test
    void test() {

    }

    static class Cache<K, V> {
        final Map<K, V> m = new HashMap<>();
        final ReadWriteLock rwl = new ReentrantReadWriteLock();
        final Lock r = rwl.readLock();
        final Lock w = rwl.writeLock();

        V get(K key) {
            r.lock();
            try {
                return m.get(key);
            } finally {
                r.unlock();
            }
        }

        void put(K key, V value) {
            w.lock();
            try {
                m.put(key, value);
            } finally {
                w.unlock();
            }
        }
    }
}
