package com.better.concurrency.part_4_cancel;

public class Test8_interrupt_use_volatie {
    public static void main(String[] args) {
        InterruptThreadTest itt = new InterruptThreadTest();
        itt.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        itt.isStop = true;
    }

    private static class InterruptThreadTest extends Thread {
        volatile boolean isStop = false;        // 通过变量来停止线程

        public void run() {
            while (!isStop) {
                long beginTime = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + "is running");
                // 当前线程每隔一秒钟检测一次线程共享变量是否得到通知
                while (System.currentTimeMillis() - beginTime < 1000) {  // 类似sleep 1 s
                }
            }
            if (isStop) {
                System.out.println(Thread.currentThread().getName() + "is interrupted");
            }
        }
    }
}
