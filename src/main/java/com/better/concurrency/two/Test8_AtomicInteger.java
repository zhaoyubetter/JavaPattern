package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class Test8_AtomicInteger {

    AtomicInteger atomicInteger = new AtomicInteger();

    @Test
    public void test1() {
        Utils.println(atomicInteger.getAndIncrement()); // 0  原子化i++
        Utils.println(atomicInteger.get()); // 1
        Utils.println(atomicInteger.compareAndSet(1, 2));
        Utils.println(atomicInteger.get());
    }

    /**
     * 模拟
     */
    @Test
    public void test3() throws InterruptedException {
        final Thread t1 = new Thread(() -> {
            atomicInteger.getAndIncrement();
        });
        final Thread t2 = new Thread(() -> {
            atomicInteger.getAndIncrement();
        });
        final Thread t3 = new Thread(() -> {
            atomicInteger.getAndIncrement();
        });
        final Thread t4 = new Thread(() -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicInteger.getAndIncrement();
        });
        final Thread t5 = new Thread(() -> {
            atomicInteger.getAndIncrement();
        });
        final Thread t6 = new Thread(() -> {
            atomicInteger.getAndIncrement();
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        Thread.sleep(3000);
        Utils.println(atomicInteger.get());
    }

    /**
     * 模拟
     */
    @Test
    public void test2() throws InterruptedException {
        final SimulatedCAS cas = new SimulatedCAS();
        final Thread t1 = new Thread(() -> {
            try {
                cas.addOne();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        final Thread t2 = new Thread(() -> {
            try {
                cas.addOne();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        final Thread t3 = new Thread(() -> {
            try {
                cas.addOne();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        final Thread t4 = new Thread(() -> {
            try {
                cas.addOne();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        final Thread t5 = new Thread(() -> {
            try {
                cas.addOne();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        final Thread t6 = new Thread(() -> {
            cas.count = 8;
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t5.join();
        t6.join();
        Thread.sleep(3000);
        Utils.println(cas.count);
    }


    static class SimulatedCAS {
        volatile int count;

        // 实现count+=1
        public void addOne() throws InterruptedException {
            int newValue;
            // 自旋
            do {        // 这里不会死循环的，每次都获取最新值，循环就是 CPU 空转
                newValue = count + 1;        // ①
                Thread.sleep(5);        // 这里可能线程修改了 count 值
                Utils.println("count: " + count);
            } while (count != cas(count, newValue)); //② 如果不相同，get the newest value again.
        }

        // 模拟实现CAS，仅用来帮助理解
        synchronized int cas(int expect, int newValue) {
            // 读目前count的值
            int curValue = count;
            // 比较目前count值是否==期望值
            if (curValue == expect) {
                Utils.println(Thread.currentThread().getName() + " count:" + count + ", cur:" + curValue + ", expect:" + expect);
                // 如果是，则更新count的值
                count = newValue;
            } else {
                Utils.println(Thread.currentThread().getName() + String.format("count:%s, except:%s, newvalue:%s", curValue, expect, newValue));
            }
            // 返回写入前的值
            return curValue;
        }
    }
}
