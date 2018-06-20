package com.better.concurrency.part_6_lock;

import com.better.Utils;

import java.util.Random;

/**
 * 经测，没出现死锁现象
 */
public class Test4_demonstrateDeadlock {
    public static void main(String... a) {
        final int num_thread = 1000;
        final int num_account =5;
        final int num_iterations = 1000000;

        final Random ran = new Random();
        final Account[] accounts = new Account[num_account];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account("account: " + i, 1000);
        }

        class TransferThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < num_iterations; i++) {
                    int from = ran.nextInt(num_account);
                    int to = ran.nextInt(num_account);
                    try {
                        tranfer(accounts[from], accounts[to], ran.nextInt(500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (int i = 0; i < num_thread; i++) {
            new TransferThread().start();
        }
    }

    private static void tranfer(Account from, Account to, int amount) throws InterruptedException {

        // 加时锁
        final Object tieLock = new Object();

        class Helper {
            public void tran() {
                from.minus(amount);
                to.plus(amount);
            }
        }

        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        if (fromHash < toHash) {
            synchronized (from) {
                Thread.sleep(20);
                synchronized (to) {
                    new Helper().tran();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (to) {
                Thread.sleep(40);
                synchronized (from) {
                    new Helper().tran();
                }
            }
        } else {
            synchronized (tieLock) {
                Thread.sleep(10);
                synchronized (from) {
                    synchronized (to) {
                        new Helper().tran();
                    }
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
            Utils.println(name + " old: " + balance + ", after minus: " + (balance - b));
            balance -= b;
        }
    }
}
