package com.better.concurrency.part_6_lock;

import com.better.Utils;

import java.util.Random;

/**
 * 动态的锁顺序死锁,与test1类似
 */
public class Test2_differlock_deadLock {
    public static void main(String... a) {
        final Account ab = new Account("better", 5000);
        final Account cc = new Account("cc", 3000);

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
                    tranfer(cc, ab, 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void tranfer(Account from, Account to, int amount) throws InterruptedException {
        synchronized (from) {
            Thread.sleep(100);
            synchronized (to) {
                if (from.getBalance() < amount) {
                    throw new RuntimeException("余额不足。。。");
                } else {
                    from.minus(amount);
                    to.plus(amount);
                }
            }
        }
    }

    private static class Account {
        private int balance = 0;
        private final String name;

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
            Utils.println(name +    " old: " + balance + ", after minus: " + (balance - b));
            balance -= b;
        }
    }
}
