package com.better.concurrency.part_3_executor;

import com.better.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class Test1_executor {

    public static void main(String[] args) throws InterruptedException {
        new ThreadTaskExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Utils.println("thread: " + Thread.currentThread().getName() + " starting...");
            }
        });
    }

    static class ThreadTaskExecutor implements Executor {
        @Override
        public void execute(@NotNull Runnable command) {
            new Thread(command).start();
        }
    }
}
