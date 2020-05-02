package com.better.concurrency.two;


import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock
 * 锁降级
 */
public class Test6_StampedLock3 {

    @org.junit.Test
    public void test() throws InterruptedException {
        final Test t = new Test();

    }


    private static class Test {
        private double x, y;
        final StampedLock sl = new StampedLock();

        void moveIfAtOrigin(double newX, double newY) {
            long stamp = sl.readLock();
            try {
                while (x == 0.0 && y == 0.0) {
                    // 尝试升级为写锁
                    long ws = sl.tryConvertToWriteLock(stamp);
                    if (ws != 0L) {
                        x = newX;
                        y = newY;
                        stamp = ws;  // stamp 改变
                        break;
                    } else {
                        sl.unlockRead(stamp);
                        stamp = sl.writeLock();
                    }
                }
            } finally {
                sl.unlock(stamp);
            }
        }
    }
}