package com.better.concurrency.part_8_lock;

import com.better.Utils;
import com.better.concurrency.anno.ThreadSafe;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition 显示锁
 * 每个内置锁只能有一个相关联的条件队列
 * 显示Lock可带有多个条件谓词
 */
public class Test6_Condition {
    public static void main(String[] args) {

        ConditionBoundedBuffer boundedBuffer = new ConditionBoundedBuffer<String>(10);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        boundedBuffer.put("a");
                        Utils.println("put ----> a");
                        Thread.sleep(3000);

                        boundedBuffer.put("b");
                        Utils.println("put ----> a");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(new Random().nextInt(1000));
                        Utils.println("take ----> " + boundedBuffer.take());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @ThreadSafe
    private static class ConditionBoundedBuffer<V> {
        private final V[] buffer;
        private int tail;
        private int head;
        private int count;

        private final Lock lock = new ReentrantLock();

        // 条件谓词：notFull (count < buffer.length)
        private final Condition notFull = lock.newCondition();
        // 条件谓词：notEmpty (count >  0)
        private final Condition notEmpty = lock.newCondition();

        public ConditionBoundedBuffer(int capacity) {
            buffer = (V[]) new Object[capacity];
        }

        public void put(V v) throws InterruptedException {
            lock.lock();
            try {
                while (count == buffer.length) {
                    notFull.await();        // 满时，阻塞写线程
                }
                buffer[tail] = v;
                if (++tail == buffer.length) {
                    tail = 0;
                }
                count++;
                notEmpty.signal();          // 通知非空
            } finally {
                lock.unlock();
            }
        }

        public V take() throws InterruptedException {
            lock.lock();
            try {
                while (count == 0) {
                    notEmpty.await();    // 空时，阻塞读线程
                }

                V v = buffer[head];
                buffer[head] = null;
                if (++head == buffer.length) {
                    head = 0;
                }
                --count;
                notFull.signal();
                return v;
            } finally {
                lock.unlock();
            }
        }
    }
}
