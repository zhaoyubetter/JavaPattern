package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CompletableFuture
 */
public class Test11_completableFuture {

    void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * AND 汇聚关系
     */
    @Test
    public void test1() {
        //任务1：洗水壶->烧开水
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                Utils.println("runAsync1 -> " + Thread.currentThread().getName());
                Utils.println("T1:洗水壶。。。");
                sleep(1, TimeUnit.SECONDS);

                Utils.println("T1:烧开水。。。");
                sleep(3, TimeUnit.SECONDS);
            }
        });

        //任务2：洗茶壶->洗茶杯->拿茶叶
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                Utils.println("runAsync2 -> " + Thread.currentThread().getName());

                Utils.println("T2:洗茶壶。。。");
                sleep(1, TimeUnit.SECONDS);

                Utils.println("T2:洗茶杯。。。");
                sleep(1, TimeUnit.SECONDS);

                Utils.println("T2:拿茶叶。。。");
                sleep(1, TimeUnit.SECONDS);

                return "功夫";
            }
        });

        /*
            thenCombine f2 所在线程
         */
        //任务3：任务1和任务2完成后执行：泡茶
        CompletableFuture<String> f3 = f1.thenCombine(f2, (__, tf) -> {
            Utils.println("T1: 拿到茶叶：" + tf);
            Utils.println("T1: 泡茶...");
            Utils.println("thenCombine -> " + Thread.currentThread().getName());
            return "好茶: " + tf;
        });

        // 等待任务3执行结果
        Utils.println(f3.join());
        Utils.println("==============================================");

        /*
           1.use thenAcceptBoth
            结果在 main Thread
           2.use thenAcceptBothAsync
            则在子线程
         */
        // 等待任务3执行
        CompletableFuture<Void> f4 = f1.thenAcceptBoth(f2, (__, tf) -> {
            Utils.println("T1: 拿到茶叶：" + tf);
            Utils.println("T1: 泡茶...");
            Utils.println("thenAcceptBoth -> " + Thread.currentThread().getName());     // mainThread
            Utils.println("好茶: " + tf);
        });

        f4.join();

        Utils.println("=================================");

        /*
            use runAfterBoth
            结果在 main Thread
         */
        CompletableFuture<Void> f5 = f1.runAfterBoth(f2, new Runnable() {
            @Override
            public void run() {
                try {
                    final String result = f2.get();
                    Utils.println("T1: 拿到茶叶：" + result);
                    Utils.println("T1: 泡茶...");
                    Utils.println("好茶: " + result);
                    Utils.println("runAfterBoth -> " + Thread.currentThread().getName());     // mainThread
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        f5.join();
    }

    /**
     * CompletionStage 接口
     * 串行关系
     */
    @Test
    public void test2() {
        // 串行执行

        // 1. 通过 supplyAsync 启动一个异步线程
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                Utils.println("supplyAsync -> " + Thread.currentThread().getName());
                return "Hello World";
            }
        }).thenApply(new Function<String, String>() {
            @Override
            public String apply(String s) {
                Utils.println("thenApply1 -> " + Thread.currentThread().getName());
                return s + " better";
            }
        }).thenApply(new Function<String, String>() {
            @Override
            public String apply(String s) {
                // 注意：这里回到 main 了，有点奇怪 TODO 不理解
                Utils.println("thenApply2 -> " + Thread.currentThread().getName());
                return s.toUpperCase();
            }
        });

        f0.join();
    }

    /**
     * OR 汇聚关系
     * applyToEither
     */
    @Test
    public void test3() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(new Supplier<java.lang.String>() {
            @Override
            public String get() {
                Utils.println("supplyAsync1 -> " + Thread.currentThread().getName());
                int t = new Random(3).nextInt();
                sleep(t, TimeUnit.SECONDS);
                return "" + t;
            }
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(new Supplier<java.lang.String>() {
            @Override
            public String get() {
                Utils.println("supplyAsync2 -> " + Thread.currentThread().getName());
                int t = new Random(5).nextInt();
                sleep(t, TimeUnit.SECONDS);
                return "" + t;
            }
        });

        CompletableFuture<String> f3 = f1.applyToEither(f2, new Function<String, String>() {
            @Override
            public String apply(String s) {
                // main
                Utils.println("applyToEither -> " + Thread.currentThread().getName());
                return s;
            }
        });

        f3.join();
    }

    /**
     * 异常捕获
     */
    @Test
    public void test4() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(new Supplier<java.lang.Integer>() {
            @Override
            public Integer get() {
                return 7 / 0;
            }
        }).thenApply(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer * 10;
            }
        }).exceptionally(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) {
                // 发生异常了，返回 0
                return 0;
            }
        }).whenComplete(new BiConsumer<Integer, Throwable>() {
            @Override
            public void accept(Integer integer, Throwable throwable) {
                Utils.println("consumered");
            }
        });

        Utils.println(f1.join());
    }
}
