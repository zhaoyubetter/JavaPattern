package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 批量异步任务
 */
public class Test12_CompletionService {

    final ExecutorService executor = Executors.newFixedThreadPool(3);
    final CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);

    /**
     * 一个成功，其他取消
     */
    @Test
    public void testForking() throws InterruptedException, ExecutionException {
        final List<Future<Integer>> futureList = new ArrayList<>(3);
        futureList.add(cs.submit(() -> getResult1()));
        futureList.add(cs.submit(() -> getResult2()));
        futureList.add(cs.submit(() -> getResult3()));

        Integer result = 0;
        try {
            for (int i = 0; i < 3; i++) {
                result = cs.take().get();
                if(result != null) { //简单地通过判空来检查是否成功返回
                    break;
                }
            }
        } finally {
            futureList.forEach(it -> it.cancel(true));
        }
        Utils.println(result);
    }

    private int getResult1() {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            Utils.println(Thread.currentThread().getName() + " interrupted...");
        }
        return 1;
    }

    private int getResult2()  {
        try {
            Thread.sleep(2200);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            Utils.println(Thread.currentThread().getName() + " interrupted...");
        }
        return 2;
    }

    private int getResult3()  {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            Utils.println(Thread.currentThread().getName() + " interrupted...");
        }
        return 3;
    }
}
