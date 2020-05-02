package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test7_CountDownLatch1 {

    // sum is not safe
    int sum = 0;

    // use join
    @Test
    public void test1() throws InterruptedException {
        final Test1 test = new Test1();

        Thread t1 = new Thread(() -> {
            try {
                sum += test.fun1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                sum += test.fun2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long startTime = System.currentTimeMillis();

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        Utils.println(String.format("sum:%s, cost: %s", sum, System.currentTimeMillis() - startTime));
    }

    int count = 2;
    final Object lock = new Object();

    // use wait()，notify()
    @Test
    public void test2() throws InterruptedException {
        final Test1 test = new Test1();

        Thread t1 = new Thread(() -> {
            try {
                sum += test.fun1();
                count--;
                synchronized (lock) {
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                sum += test.fun2();
                count--;
                synchronized (lock) {
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        long startTime = System.currentTimeMillis();

        synchronized (lock) {
            while (count > 0) {
                lock.wait();
            }
        }

        Utils.println(String.format("sum:%s, cost: %s", sum, System.currentTimeMillis() - startTime));
    }


    // use ReentrantLock & Condition
    final ReentrantLock reentrantLock = new ReentrantLock();
    final Condition c1 = reentrantLock.newCondition();

    @Test
    public void test3() throws InterruptedException {
        final Test1 test = new Test1();

        Thread t1 = new Thread(() -> {
            try {
                sum += test.fun1();
                count--;
                reentrantLock.lock();
                c1.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                sum += test.fun2();
                count--;
                reentrantLock.lock();
                c1.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        });

        t1.start();
        t2.start();

        long startTime = System.currentTimeMillis();

        reentrantLock.lock();
        try {
            while (count > 0) {
                c1.await();
            }
        } finally {
            reentrantLock.unlock();
        }

        Utils.println(String.format("sum:%s, cost: %s", sum, System.currentTimeMillis() - startTime));
    }

    private static class Test1 {
        public int fun1() throws InterruptedException {
            Thread.sleep(1000);
            return 1;
        }

        public int fun2() throws InterruptedException {
            Thread.sleep(1500);
            return 2;
        }
    }
}
