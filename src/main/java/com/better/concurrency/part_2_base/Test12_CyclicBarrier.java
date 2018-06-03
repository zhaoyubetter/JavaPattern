package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Test12_CyclicBarrier {
    public static void main(String... args) {
        CyclicBarrier cb = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                Utils.println("集合完毕。。。");
            }
        });
        for (int i = 0; i < 3; i++) {
            new Thread(new WorkThread(cb)).start();
        }
    }

    private final static class WorkThread implements Runnable {

        private final CyclicBarrier cb;

        public WorkThread(CyclicBarrier cb) {
            this.cb = cb;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(new Random().nextInt(2000));
                Utils.println("thread" + Thread.currentThread().getName() + "到达，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
