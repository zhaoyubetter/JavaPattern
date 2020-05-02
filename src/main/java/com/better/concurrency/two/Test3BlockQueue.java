package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test3BlockQueue {

    @Test
    public void test1() throws InterruptedException {
        final BlockingQueue<String> blockingQueue = new BlockingQueue<>();
        final Thread t1 = new Thread(() -> {
            try {
                while (true) {
                    final String str = blockingQueue.deq();
                    Utils.println(str);
                }
            } catch (InterruptedException e) {
            }
        });

        final Thread t2 = new Thread(() -> {
            try {
                blockingQueue.req("a");
                blockingQueue.req("b");
                blockingQueue.req("c");
                blockingQueue.req("d");
                blockingQueue.req("e");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }

    @Test
    public void test2() throws InterruptedException {
        final BlockingQueue2<String> blockingQueue = new BlockingQueue2<>();
        final Thread t1 = new Thread(() -> {
            try {
                while (true) {
                    final String str = blockingQueue.deq();
                    Utils.println(str);
                }
            } catch (InterruptedException e) {
            }
        });

        final Thread t2 = new Thread(() -> {
            try {
                blockingQueue.req("1");
                blockingQueue.req("2");
                blockingQueue.req("3");
                blockingQueue.req("4");
                blockingQueue.req("5");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }

    static class BlockingQueue<T> {
        final Lock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();
        final LinkedList<T> queue = new LinkedList();
        final int size = 3;

        void req(T t) throws InterruptedException {
            lock.lock();
            try {
                while (queue.size() >= size) {  // queue is full
                    Utils.println("Queue is full, then wait deque...");
                    notFull.await();
                }
                Utils.println(">>> req: " + t);
                queue.addLast(t);
                notEmpty.signal();  // notify can deq
            } finally {
                lock.unlock();
            }
        }

        T deq() throws InterruptedException {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    Utils.println("Queue is empty, then wait requeue...");
                    notEmpty.await();
                }
                final T t = queue.removeFirst();
                Utils.println("<<< deq: " + t);
                notFull.signal();
                return t;
            } finally {
                lock.unlock();
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////
    // use old
    static class BlockingQueue2<T> {
        final Object lock = new Object();
        final LinkedList<T> queue = new LinkedList();
        final int size = 3;

        synchronized void req(T t) throws InterruptedException {
            try {
                while (queue.size() >= size) {  // queue is full
                    Utils.println("Queue is full, then wait deque...");
                    this.wait();
                }
                Utils.println(">>> req: " + t);
                queue.addLast(t);
                this.notify();
            } finally {
            }
        }

        synchronized T deq() throws InterruptedException {
            try {
                while (queue.isEmpty()) {
                    Utils.println("Queue is empty, then wait requeue...");
                    this.wait();
                }
                final T t = queue.removeFirst();
                Utils.println("<<< deq: " + t);
                this.notifyAll();
                return t;
            } finally {
            }
        }
    }
}
