package com.better.concurrency.part_4_cancel;

public class Test10_interrupt_not_interrupt {

    public void deathLock(Object lock1, Object lock2) {
        try {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " is running");
                // 让另外一个线程获得另一个锁
                Thread.sleep(10);
                // 造成死锁
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName());
                }
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " is interrupted");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        final Test10_interrupt_not_interrupt itt = new Test10_interrupt_not_interrupt();
        final Object lock1 = new Object();
        final Object lock2 = new Object();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                itt.deathLock(lock1, lock2);
            }
        }, "A");
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                itt.deathLock(lock2, lock1);
            }
        }, "B");

        t1.start();
        t2.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 中断线程t1、t2
        t1.interrupt();
        t2.interrupt();
    }
}
