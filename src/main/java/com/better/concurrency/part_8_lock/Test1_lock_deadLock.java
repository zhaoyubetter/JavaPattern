package com.better.concurrency.part_8_lock;

import com.better.Utils;
import com.better.concurrency.part_6_lock.Test3_differlock_deadLock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock 显式锁, 解决死锁问题，但只能一个线程进入 transfer方法
 * @see com.better.concurrency.part_6_lock.Test3_differlock_deadLock#tranfer(Test3_differlock_deadLock.Account, Test3_differlock_deadLock.Account, int)
 */
public class Test1_lock_deadLock {
    public static void main(String... a) throws InterruptedException {
        final Account ab = new Account("better", 5000);
        final Account cc = new Account("cc", 3000);

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        tranfer(ab, cc, 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                        tranfer(cc, ab, 30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    public static void tranfer(Account from, Account to, int amount) throws InterruptedException {
        if (from.lock.tryLock()) {      // 只会进来一次
            Thread.sleep(30);
            try {
                if (to.lock.tryLock()) {
                    try {
                        if (from.getBalance() < amount) {
                            throw new RuntimeException("余额不足。。。");
                        } else {
                            from.minus(amount);
                            to.plus(amount);
                        }
                    } finally {
                        to.lock.unlock();
                    }
                }
            } finally {
                from.lock.unlock();
            }
        }

//        synchronized (from) {
//            Thread.sleep(100);
//            synchronized (to) {
//                if (from.getBalance() < amount) {
//                    throw new RuntimeException("余额不足。。。");
//                } else {
//                    from.minus(amount);
//                    to.plus(amount);
//                }
//            }
//        }
    }

    private static class Account {
        private int balance = 0;
        private final String name;
        public final Lock lock = new ReentrantLock();

        public Account(String name, int balance) {
            this.balance = balance;
            this.name = name;
        }

        public int getBalance() {
            return balance;
        }

        public void plus(int b) {
            Utils.println(name + " old: " + balance + ", after plus: " + (balance + b));
            balance += b;
        }

        public void minus(int b) {
            Utils.println(name + " old: " + balance + ", after minus: " + (balance - b));
            balance -= b;
        }
    }
}
