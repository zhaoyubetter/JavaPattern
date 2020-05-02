package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test7_CountDownLatch2 {

    // sum is not safe
    int sum = 0;

    // 创建2个线程的线程池
    Executor executor = Executors.newFixedThreadPool(2);

    // use join
    @Test
    public void test1() throws InterruptedException {
        final Test1 test = new Test1();
        final CountDownLatch latch = new CountDownLatch(2);

        executor.execute(() -> {
            try {
                sum += test.fun1();
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            try {
                sum += test.fun2();
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long startTime = System.currentTimeMillis();
        latch.await();  // 等待结果
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
