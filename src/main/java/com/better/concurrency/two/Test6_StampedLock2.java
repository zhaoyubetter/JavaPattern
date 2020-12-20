package com.better.concurrency.two;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock
 * 导致 cpu 100%
 */
public class Test6_StampedLock2 {
    @Test
    public void test() throws InterruptedException {

        final StampedLock lock = new StampedLock();
        Thread T1 = new Thread(() -> {
            // 获取写锁
            lock.writeLock();
            // 永远阻塞在此处，不释放写锁
            LockSupport.park();
        });
        T1.start();

        // 保证T1获取写锁
        Thread.sleep(100);

        Thread T2 = new Thread(() ->
                //阻塞在悲观读锁
                lock.readLock()     // lock.readLockInterruptibly()
        );
        T2.start();

        // 保证T2阻塞在读锁
        Thread.sleep(100);

        //中断线程T2
        //会导致线程T2所在CPU飙升
        //会导致线程T2所在CPU飙升
        T2.interrupt();
        T2.join();
    }


}
