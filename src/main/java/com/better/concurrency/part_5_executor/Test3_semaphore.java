package com.better.concurrency.part_5_executor;

import com.better.Utils;

import java.util.concurrent.*;

/**
 * 控制任务提交速率
 */
public class Test3_semaphore {

    public static void main(String... a) throws Exception {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10));
        // 调用者策略
//        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());  // 设置饱和策略
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        final Semaphore semaphore = new Semaphore(3);
        BoundedExecutor executor = new BoundedExecutor(threadPoolExecutor, semaphore);

        for (int i = 0; i < 100; i++) {
            try {
                executor.submitTask(new Test3_semaphore.Task(i));
            } catch (Exception e) {
                Utils.println("拒接服务，提交的任务太多了。。。。");
                Utils.println(e.getMessage());
            }
        }
    }

    private static class BoundedExecutor {
        private final Executor exec;
        private final Semaphore semaphore;

        public BoundedExecutor(Executor exec, Semaphore semaphore) {
            this.exec = exec;
            this.semaphore = semaphore;
        }

        void submitTask(final Runnable cmd) throws InterruptedException {
            semaphore.acquire();
            try {
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            cmd.run();
                        } finally {
                            semaphore.release();
                        }
                    }
                });
            } catch (RejectedExecutionException e) {
                Utils.println("拒接服务，提交的任务太多了。。。。");
                semaphore.release();        //
            }
        }
    }

    private static class Task implements Runnable{
        private int name;

        public Task(int name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Utils.println("======>" + Thread.currentThread().getName() + "___" + name);
        }
    }

}
