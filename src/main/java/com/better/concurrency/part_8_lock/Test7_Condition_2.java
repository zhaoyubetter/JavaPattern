package com.better.concurrency.part_8_lock;


import com.better.Utils;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交替输出 一个Condition
 */
public class Test7_Condition_2 {

    private static final String[] units_olds = {"壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾"};
    private static final String[] units_now = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

    private static final Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        //   for (int i = 0; i < units_now.length; i++) {
        //            printlnOld(i);
        //        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < units_now.length; i++) {
                        printlnOld(i);
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < units_now.length; i++) {
                        println_now(i);
                    }
                }
            }
        }).start();
    }

    private static void printlnOld(final int index) {
        try {
            lock.lock();
//            Utils.println("old");
            Thread.sleep(new Random().nextInt(1000));
            Utils.println(units_olds[index]);
            condition.signal();   // 完成工作，唤醒其他，进入休眠，等待唤醒
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void println_now(int index) {
        try {
            lock.lock();
//            Utils.println("now");
            Thread.sleep(new Random().nextInt(2000));
            Utils.println(units_now[index]);
            condition.signal();
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
