package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Test13_fork_join {

    @Test
    public void test1() {
        // 创建分治任务池
        ForkJoinPool fjp = new ForkJoinPool(4);
        // 分支任务
        Fibonacci fib = new Fibonacci(30);
        // 启动任务
        final int result = fjp.invoke(fib);
        Utils.println(result);
    }

    /**
     * 统计单词个数
     */
    @Test
    public void test2() {
        String[] fc = {"hello world", "hello me", "hello fork", "hello join", "fork join in world"};
        //创建ForkJoin线程池
        ForkJoinPool fjp = new ForkJoinPool(3);

    }

    //递归任务
    static class Fibonacci extends RecursiveTask<Integer> {

        final int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }

            //Utils.println(Thread.currentThread().getName());

            Fibonacci f1 = new Fibonacci(n - 1);
            // 创建子任务
            f1.fork();  // fork() 方法会异步地执行一个子任务
            Fibonacci f2 = new Fibonacci(n - 2);
            //等待子任务结果，并合并结果
            return f2.compute() + f1.join();    // join() 方法则会阻塞当前线程来等待子任务的执行结果
        }
    }
}
