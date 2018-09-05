package com.better.concurrency.part_8_lock;

import com.better.Utils;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test10_Condition_5_many {

    public static void main(String... aa) throws InterruptedException {
        final TestCondition test = new TestCondition();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    test.manager();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    test.code();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(2000);
        test.publisTask();
    }

    private static class TestCondition {
        // boss->manager->coder
        private final Lock lock = new ReentrantLock();
        private final Condition bossCondition = lock.newCondition();
        private final Condition managerCondition = lock.newCondition();
        private final Condition coderCondition = lock.newCondition();

        void publisTask() throws InterruptedException {
            try {
                lock.lock();
                Thread.sleep(new Random().nextInt(1000));
                Utils.println("boss 发布任务，通知Manager！！！");
                managerCondition.signal();  // 通知主管,这2行，千万不能写反了
                bossCondition.await();
                Utils.println("程序员活干完了！！！");
            } finally {
                Utils.println("=====");
                lock.unlock();
            }
        }

        void manager() throws InterruptedException {
            try {
                lock.lock();
                managerCondition.await();
                Utils.println("manager 收到任务，通知程序员！！！");
                Thread.sleep(new Random().nextInt(1000));
            } finally {
                coderCondition.signal();
                lock.unlock();
            }
        }

        void code() throws InterruptedException {
            try {
                lock.lock();
                coderCondition.await();
                Utils.println("程序员开始干活。。。");
                Thread.sleep(new Random().nextInt(1000));
            } finally {
                bossCondition.signal();
                lock.unlock();
            }
        }
    }
}
