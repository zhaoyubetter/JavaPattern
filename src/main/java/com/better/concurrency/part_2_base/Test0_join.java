package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.Random;

public class Test0_join {
    public static void main(String[] args) throws InterruptedException {
        Thread pp = new Thread(new ThreadTest());
        pp.start();

        int i = 0;
        while (i <=10 ) {
            Thread.sleep(200);
            if (i == 10) { // i为10时，main线程等待pp线程执行完后，才继续执行
                try {
                    // 把指定的线程加入到当前线程，2个线程合并为顺序执行的线程
                    pp.join();  // 把pp所对应的线程合并到调用pp.join()语句的线程中
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            Utils.println("main Thread: " + i++);
        }

        Utils.println("main Thread stop");
    }

    static class ThreadTest implements Runnable {
        public void run() {
            int i = 0;
            while (true) {
                try {
                    Thread.sleep(new Random().nextInt(1000));
                    System.out.println(Thread.currentThread().getName() + " " + i++);
                    if (i == 20) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
