package com.better.concurrency.part_4_cancel;

/**
 * 中断阻塞线程
 */
public class Test9_interrupt_block {
    public static void main(String[] args) {
        InterruptThreadTest3 itt = new InterruptThreadTest3();
        itt.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 设置线程的中断标志位
        itt.interrupt();
    }

    private static class InterruptThreadTest3 extends Thread {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " is running");
                try {
                    System.out.println(Thread.currentThread().getName() + " Thread.sleep begin");
                    Thread.sleep(1000);     // ====》  阻塞的方法
                    System.out.println(Thread.currentThread().getName() + " Thread.sleep end");
                } catch (InterruptedException e) {
                    //由于调用sleep()方法清除状态标志位 所以这里需要再次重置中断标志位 否则线程会继续运行下去
                    Thread.currentThread().interrupt();     // 重新设置线程的中断状态
                    e.printStackTrace();
                }
            }
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + "is interrupted <<<<<<<< " );
            }
        }
    }
}
