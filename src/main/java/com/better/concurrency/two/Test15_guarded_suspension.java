package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * Guarded Suspension。所谓 Guarded Suspension，直译过来就是“保护性地暂停”
 */
public class Test15_guarded_suspension {

    @Test
    public void test1() throws InterruptedException {
        final GuardedObject<Integer> obj = new GuardedObject();

        // 线程1获取数据，获取不到等待
        final Thread t1 = new Thread(
                () -> Utils.println(obj.get(it -> it != null && it > 20))
        );

        // 线程2提供数据
        final Thread t2 = new Thread(
                () -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    obj.onChange(5);
                }
        );

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    static class GuardedObject<T> {
        // 受保护对象
        T obj;
        final Lock lock = new ReentrantLock();
        final Condition done = lock.newCondition();
        final int timeout = 2;

        /**
         * 获取受保护对象
         */
        T get(Predicate<T> p) {
            lock.lock();
            try {
                while (!p.test(obj)) {
                    try {
                        done.await(timeout, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }

            // 返回受保护对象
            return obj;
        }

        /**
         * 事件通知方法
         */
        void onChange(T obj) {
            lock.lock();
            try {
                this.obj = obj;
                done.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

}
