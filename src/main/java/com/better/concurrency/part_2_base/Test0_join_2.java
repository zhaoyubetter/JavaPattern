package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.Random;

public class Test0_join_2 {
    public static void main(String[] args) throws InterruptedException {
        Thread pp = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    try {
                        Thread.sleep(new Random().nextInt(500));
                        System.out.println(Thread.currentThread().getName() + " " + i++);
                        if (i == 100) {
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        pp.start();
        pp.join();  // 把pp所对应的线程合并到调用pp.join()语句的线程中,即：main需要等待pp线程结束，才能继续执行main

        Utils.println("main Thread stop");
    }

}
