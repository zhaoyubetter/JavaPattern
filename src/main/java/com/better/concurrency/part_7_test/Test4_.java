package com.better.concurrency.part_7_test;

import com.better.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Test4_ {
    public static void main(String[] args) throws InterruptedException {
        TestingThreadFactory threadFactory = new TestingThreadFactory();
        int MAX_SIZE = 10;

        ExecutorService exec = Executors.newFixedThreadPool(MAX_SIZE, threadFactory);      // 最大10个
        for (int i = 0; i < 10 * MAX_SIZE; i++) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        for (int i = 0; i < 20 && threadFactory.numCreated.get() < MAX_SIZE; i++) {
            Thread.sleep(100);
        }

        Utils.println(threadFactory.numCreated.get() == MAX_SIZE);
        exec.shutdown();
    }

    private static class TestingThreadFactory implements ThreadFactory {
        public final AtomicInteger numCreated = new AtomicInteger();
        private final ThreadFactory factory = Executors.defaultThreadFactory();

        @Override
        public Thread newThread(@NotNull Runnable r) {
            numCreated.incrementAndGet();
            Utils.println("thread name: " + Thread.currentThread().getName() + ", count: " + numCreated.get());
            return factory.newThread(r);
        }
    }
}