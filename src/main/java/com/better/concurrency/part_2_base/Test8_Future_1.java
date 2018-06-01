package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.concurrent.*;

public class Test8_Future_1 {
    public static void main(String... ag) throws ExecutionException, InterruptedException {
        // Future 是一个接口，实际泛返回类型是 FutureTask
        Future<Integer> result = Executors.newSingleThreadExecutor().submit(new AddTask(5, 19));
        Utils.println("cancelled: " + result.isCancelled() + ", done: " + result.isDone());
        Utils.print(result.get());      // 阻塞,如果计算未完成，则等待任务完成
        Utils.println("cancelled: " + result.isCancelled() + ", done: " + result.isDone());
        Utils.println("aaa");
    }


    static class AddTask implements Callable<Integer> {
        private int a, b;

        public AddTask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Integer call() throws Exception {
            Thread.sleep(1000);
            Integer result = a + b;
            return result;
        }
    }
}
