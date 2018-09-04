package com.better.concurrency.part_5_executor;

import com.better.Utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * 线程工厂
 */
public class Test4_threadFatory {

    public static void main(String... a) throws Exception {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 20, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10));
        // 调用者策略
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());  // 设置饱和策略
        threadPoolExecutor.setThreadFactory(new MyThreadFactory("betterThreadPool"));

        for (int i = 0; i < 100; i++) {
            try {
                final int bb = i;
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(300);
                            // TODO: 打印的有些是主线程
                            Utils.println(Thread.currentThread().getName() + " do work..." + bb);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Utils.println("activeCount: " + threadPoolExecutor.getActiveCount() + ", taskCount: " + threadPoolExecutor.getTaskCount() +
                        ", " + threadPoolExecutor.getActiveCount());
            } catch (Exception e) {
                Utils.println("拒接服务，提交的任务太多了。。。。");
                Utils.println(e.getMessage());
            }
        }

        Utils.println("================================================");
        Utils.println("=====================>>>>>>>>>>>>===============");
        Utils.println("=====================<<<<<<<<<<<<===============");
        while(true) {
            Thread.sleep(1000);
            Utils.println("activeCount: " + threadPoolExecutor.getActiveCount() + ", taskCount: " + threadPoolExecutor.getTaskCount() +
                    ", " + threadPoolExecutor.getActiveCount());
        }
    }

    /**
     * 自定义线程工厂类
     */
    private static class MyThreadFactory implements ThreadFactory {
        private String poolName;

        public MyThreadFactory(String poolName) {
            this.poolName = poolName;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new MyAppThread(r, poolName);
        }
    }

    /**
     * 定制thread类
     */
    private static class MyAppThread extends Thread {
        final String DEFAULT_NAME = "MyAppThread";
        private static volatile boolean debugLIfeCycle = true;
        private static final AtomicInteger created = new AtomicInteger();
        private static final AtomicInteger alive = new AtomicInteger();
        private static final Logger log = Logger.getLogger("aaa");

        static {
            log.info("aaa");
        }

        public MyAppThread(Runnable runnable, String name) {
            super(runnable, name + "-" + created.incrementAndGet());
            setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    log.info("UNCAUGHT in thread " + t.getName() + ", errorMessage: " + e.getMessage());
                }
            });
        }

        @Override
        public void run() {
            boolean debug = debugLIfeCycle;     // 栈封闭
            if (debug) {
                log.info(" Created " + getName());
            }
            try {
                alive.incrementAndGet();
                super.run();
            } finally {
                alive.decrementAndGet();
                if (debug) {
                    log.info(" Exiting " + getName());
                }
            }
        }

        public static int getThreadCreated() {
            return created.get();
        }

        public static int getThreadsAlive() {
            return alive.get();
        }

        public static boolean isDebugLIfeCycle() {
            return debugLIfeCycle;
        }

        public static void setDebugLIfeCycle(boolean debugLIfeCycle) {
            MyAppThread.debugLIfeCycle = debugLIfeCycle;
        }
    }
}

