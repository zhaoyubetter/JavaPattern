package com.better.concurrency.part_5_executor;

import com.better.Utils;

import java.util.concurrent.*;

/**
 * 线程饥饿死锁,任务相互依赖
 */
public class Test1_threaddeadLock {

    final static ExecutorService executor = Executors.newSingleThreadExecutor();
//    final static ExecutorService executor = Executors.newFixedThreadPool(3);


    public static void main(String... a) throws Exception {
        Utils.println(executor.submit(new RenderPageTask()).get());
    }

   private static class RenderPageTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = executor.submit(new LoadTask());
            footer = executor.submit(new LoadTask());
            return header.get() + footer.get();     // task在等待子task的结果，主task要返回结果，发现header子task还没有执行，死锁
        }
    }

    private static class LoadTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(300);
            return "loadTask";
        }
    }

}
