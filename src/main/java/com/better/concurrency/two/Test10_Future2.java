package com.better.concurrency.two;


import com.better.Utils;
import com.sun.xml.internal.ws.util.CompletedFuture;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Test10_Future2 {

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        // 创建任务T2的FutureTask
        FutureTask<String> ft2 = new FutureTask<>(new T2Task());
        // 创建任务T1的 FutureTask
        FutureTask ft1 = new FutureTask<>(new T1Task(ft2));

        // 线程1
        Thread t1 = new Thread(ft1);
        Thread t2 = new Thread(ft2);
        t1.start();
        t2.start();

        Utils.println(ft2.get());

    }

    /**
     * 依赖 T2Task 的执行结果
     */
    static class T1Task implements Callable<String> {

        FutureTask<String> ft2;

        public T1Task(FutureTask<String> ft) {
            this.ft2 = ft;
        }

        @Override
        public String call() throws Exception {
            System.out.println("T1:洗水壶...");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("T1:烧水...");
            TimeUnit.SECONDS.sleep(3);
            // 获取T2线程的茶叶
            String tf = ft2.get();
            System.out.println("T1:拿到茶叶:" + tf);
            return "上茶：" + ft2.get();
        }
    }

    static class T2Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("T2:洗茶壶...");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("T2:洗茶杯...");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("T2:拿茶叶...");
            TimeUnit.SECONDS.sleep(1);
            return "功夫";
        }
    }
}
