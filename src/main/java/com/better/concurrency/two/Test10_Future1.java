package com.better.concurrency.two;


import com.better.Utils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Test10_Future1 {

    final ExecutorService executor = Executors.newFixedThreadPool(1);

    /**
     * Tests Callable & Future
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        // 1. like Runnable interface
        final Callable<Integer> a = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Utils.println("callable call() is invoked.");
                return 2000;
            }
        };
        // 2. Uses Future to get Callable result
        final Future<Integer> future = executor.submit(a);   // inner will generate the FutureTask object
        Utils.println(future.get());
    }

    // Uses Thread & Future
    @Test
    public void test2() {

    }


    /**
     * Uses FutureTask
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test3() throws ExecutionException, InterruptedException {
        // Uses Callable
        final FutureTask<Integer> futureTask1 = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return 100 + 500;
            }
        });
        executor.submit(futureTask1);
        Utils.println(futureTask1.get());   // Blocked until get result.

        // Uses Runnable，这个方式有点麻烦
        final Map<String, String> map = new HashMap<>();
        final FutureTask< Map<String, String>> futureTask2 = new FutureTask<>(new Task(map), map);
        executor.submit(futureTask2);
        final Map<String, String> stringStringMap = futureTask2.get();
        Utils.println(stringStringMap.get("result"));
    }

    private static class Task implements Runnable {
        private Map<String, String> flag;
        public Task(Map<String, String> flag) {
            this.flag = flag;
        }
        @Override
        public void run() {
            int i = 4 / 2;
            flag.put("result", "" + i);
        }
    }
}
