package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadLocal
 */
public class Test14_thread_local {

    @Test
    public void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Utils.println(Thread.currentThread().getName() + "->" + ThreadId.get());
            Utils.println(Thread.currentThread().getName() + "->" + ThreadId.get());
        }
        );
        Thread t2 = new Thread(() -> Utils.println(Thread.currentThread().getName() + "->" + ThreadId.get()));
        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    static class ThreadId {
        static final AtomicInteger newxtId = new AtomicInteger(0);
        static final ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> newxtId.getAndIncrement());

        static int get() {
            return tl.get();
        }
    }

}
