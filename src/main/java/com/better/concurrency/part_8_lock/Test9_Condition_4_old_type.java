package com.better.concurrency.part_8_lock;


import com.better.Utils;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用传统方法
 */
public class Test9_Condition_4_old_type {

    private static final String[] units_olds = {"壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾"};
    private static final String[] units_now = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

    private static final Lock lock = new ReentrantLock();
    private static Condition condition_old = lock.newCondition();
    private static boolean isOld = false;

    public static void main(String[] args) throws InterruptedException {
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
            while (isOld) {
                condition_old.await();
            }
            Thread.sleep(new Random().nextInt(1000));
            Utils.println(units_olds[index]);
            isOld = true;
            condition_old.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void println_now(int index) {
        try {
            lock.lock();
            while (!isOld) {
                condition_old.await();
            }
            Thread.sleep(new Random().nextInt(2000));
            Utils.println(units_now[index]);
            isOld = false;
            condition_old.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
