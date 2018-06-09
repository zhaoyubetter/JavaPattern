package com.better.concurrency.part_4_cancel;

public class Test8_interrupt_use_interrupt {
    public static void main(String[] args) {
        InterruptThreadTest itt = new InterruptThreadTest();
        itt.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 使用 interrupt
        itt.interrupt();
    }

    private static class InterruptThreadTest extends Thread {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                long beginTime = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + "is running");
                // 当前线程每隔一秒钟检测一次线程共享变量是否得到通知
                while (System.currentTimeMillis() - beginTime < 1000) {  // 类似sleep 1 s
                }
            }
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + "is interrupted");
            }
        }
    }
}
