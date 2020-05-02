package com.better.concurrency.two;

public class Test1Sync1 {

    public static void main(String... args) throws InterruptedException {
        final Thread t1 = new Thread();
        t1.wait();
        Thread.sleep(0);
        t1.interrupt();
    }

}
