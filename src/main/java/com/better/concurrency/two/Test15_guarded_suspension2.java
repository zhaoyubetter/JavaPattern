package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * Guarded Suspension。所谓 Guarded Suspension，直译过来就是“保护性地暂停”
 * 多个
 * https://time.geekbang.org/column/article/94097
 */
public class Test15_guarded_suspension2 {

    /**
     * 添加 key
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        final GuardedObject<Integer> guardedObject = GuardedObject.create("12");

        // 线程1获取数据，获取不到等待
        final Thread t1 = new Thread(
                () -> Utils.println(guardedObject.get(it -> it != null && it > 20))
        );

        // 线程2提供数据
        final Thread t2 = new Thread(
                () -> {
                    try {
                        Thread.sleep(1000);
                        GuardedObject.fireEvent("12", 100);
                    } catch (InterruptedException e) {
                    }
                }
        );

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    final static class GuardedObject<T> {
        // 受保护对象
        T obj;
        final Lock lock = new ReentrantLock();
        final Condition done = lock.newCondition();
        final int timeout = 2;

        //保存所有GuardedObject
        final static Map<Object, GuardedObject> gos = new ConcurrentHashMap<>();

        // 静态方法创建GuardedObject
        static <K> GuardedObject create(K key) {
            GuardedObject go = new GuardedObject();
            gos.put(key, go);
            return go;
        }

        static <K, T> void fireEvent(K key, T obj) {
            final GuardedObject go = gos.remove(key);
            if(go != null) {
                go.onChange(obj);
            }
        }

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
        private void onChange(T obj) {
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
