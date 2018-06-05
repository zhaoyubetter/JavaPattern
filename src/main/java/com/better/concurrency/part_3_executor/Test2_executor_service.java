package com.better.concurrency.part_3_executor;

import com.better.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test2_executor_service {

    public static void main(String[] args) throws InterruptedException {


        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Executors.newCachedThreadPool();

        Executors.newFixedThreadPool(5);

    }

    
}
