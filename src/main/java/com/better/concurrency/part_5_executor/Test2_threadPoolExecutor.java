package com.better.concurrency.part_5_executor;

import com.better.Utils;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 */
public class Test2_threadPoolExecutor {
    public static void main(String... a) throws Exception {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10)) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
            }
        };


        // 调用者策略
//        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());  // 设置饱和策略
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 100; i++) {
            try {
                Utils.println(threadPoolExecutor.submit(new Task(i)));
            } catch (Exception e) {
                Utils.println("拒接服务，提交的任务太多了。。。。");
                Utils.println(e.getMessage());
            }
        }
    }

    private static class Task implements Callable<String> {
        private int name;

        public Task(int name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(3000);
            Utils.println("======>" + Thread.currentThread().getName() + "___" + name);
            return "" + Thread.currentThread().getName() + "___" + name + "";
        }
    }


}
