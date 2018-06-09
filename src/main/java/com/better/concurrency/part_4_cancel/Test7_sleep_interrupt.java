package com.better.concurrency.part_4_cancel;


import com.better.Utils;

/**
 * 执行中断,用interrupt 并不能终止线程,见下面的死循环
 */
public class Test7_sleep_interrupt {

    public static void main(String[] args) {
        final Thread tt = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Utils.println(Thread.currentThread().getName() + "Executing....");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Utils.println("中断。。。");
                    e.printStackTrace();
                } finally {
                    Utils.println(Thread.currentThread().getName() + "finished....");
                }

                /*
                try {
                    while (true) {  // 调用interrupt 并不能终止线程
                        Utils.println(Thread.currentThread().getName() + "Executing....");
                    }
                } finally {
                    Utils.println(Thread.currentThread().getName() + "finished....");
                }*/
            }
        });

        tt.start();
        tt.interrupt();     // 中断线程执行
    }
}
