package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 简单线程池
 */
public class Test9_ThreadPool {

    @Test
    public void test1() throws InterruptedException {
        // 有界队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2);
        ThreadPool pool = new ThreadPool(10, workQueue);
        pool.execute(() -> Utils.println("ok"));
    }

    static class ThreadPool {
        BlockingQueue<Runnable> workQueue;
        List<WorkerThread> threads = new ArrayList<>();

        public ThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
            this.workQueue = workQueue;
            // 创建工作线程
            for (int i = 0; i < poolSize; i++) {
                WorkerThread work = new WorkerThread();
                work.start();       // <====
                threads.add(work);
            }
        }

        void execute(Runnable cmd) throws InterruptedException {
            workQueue.put(cmd);
        }

        class WorkerThread extends Thread {
            @Override
            public void run() {
                super.run();
                // 循环取任务，并执行
                while (true) {      // ①
                    Runnable task = null;
                    try {
                        task = workQueue.take();
                        task.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
