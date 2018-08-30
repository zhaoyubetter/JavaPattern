package com.better.concurrency.part_8_lock;


import com.better.Utils;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交替输出 1个Condition，通过一个成员变量来控制
 */
public class Test8_Condition_3 {

    private static final String[] units_olds = {"壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾"};
    private static final String[] units_now = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    private static boolean isOld = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < units_now.length; i++) {
                        try {
                            printlnOld(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < units_now.length; i++) {
                        try {
                            println_now(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private static void printlnOld(final int index) throws InterruptedException {
        synchronized (units_olds) {
            while (isOld) {
                units_olds.wait();
            }
            Thread.sleep(new Random().nextInt(1000));
            Utils.println(units_olds[index]);
            isOld = true;
            units_olds.notify();
        }
    }

    private static void println_now(int index) throws InterruptedException {
        synchronized (units_olds) {
            while (!isOld) {
                units_olds.wait();
            }
            Thread.sleep(new Random().nextInt(2000));
            Utils.println(units_now[index]);
            isOld = false;
            units_olds.notify();
        }
    }
}
