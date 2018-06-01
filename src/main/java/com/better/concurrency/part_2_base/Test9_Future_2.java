package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.Random;
import java.util.concurrent.*;

/**
 * FutureTask
 */
public class Test9_Future_2 {
    public static void main(String... ag) throws ExecutionException, InterruptedException {

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return new Random().nextInt(100);
            }
        };

        // FutureTask实现RunnableFuture
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(futureTask);
        Utils.println(futureTask.get());

        // shutdown 时，main 进程，才结束
        // executorService.shutdown();

    }
}
