package com.better.concurrency.part_8_lock;


import com.better.Utils;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test7_Condition_2 {

    private static final String[] units_olds = {"壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾"};
    private static final String[] units_now = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

    private static final Lock lock = new ReentrantLock();
    private static Condition condition_old = lock.newCondition();
    private static Condition condition_new = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        //   for (int i = 0; i < units_now.length; i++) {
        //            printlnOld(i);
        //        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                printlnOld(0);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                println_now(0);
            }
        }).start();
    }

    private static void printlnOld(final int index)  {
        try {
            lock.lock();
            Thread.sleep(new Random().nextInt(1000));
            Utils.println(units_olds[index]);
            condition_old.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void println_now(int index) {
        try {
            Thread.sleep(new Random().nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
