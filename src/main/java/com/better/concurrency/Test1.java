package com.better.concurrency;


import com.better.Utils;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Test1 a = new Test1();
        a.methodA(latch);
        a.methodA(latch);
        a.methodA(latch);
        a.methodA(latch);
        a.methodA(latch);


        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("执行结束");
    }

    private int status = 0;
    private boolean hasStart = false;


    public  void methodA(CountDownLatch latch) throws InterruptedException {
        if (status == 1) {
            return;
        }

        System.out.println("methodA Called");
        methodB(latch);
    }

    public  void methodB(CountDownLatch latch) throws InterruptedException {

        /*
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        synchronized (Test1.this) {
            status = 1;
        }
        latch.countDown();
        */

        Thread.sleep(2000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                status = 1;
                latch.countDown();
            }
        }).start();
    }

}
