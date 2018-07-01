package com.better.concurrency.part_7_test;

import com.better.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 缓存的有界与阻塞，测试生产者，消费者
 *
 * @param
 */
public class Test4_BoundedBuffer {

    class BarrierTimer implements Runnable {
        private boolean started;
        private long startTime, endTime;
        @Override
        public synchronized void run() {
            long t = System.nanoTime();
            if(!started) {
                started = true;
                startTime = t;
            } else {
                endTime = t;
            }
        }
        public synchronized void clear() {
            started = false;
        }
        public synchronized long getTime() {
            return endTime - startTime;
        }
    }

    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
    private final CyclicBarrier barrier;
    private final BoundedBuffer<Integer> cc;
    private final int nTrials, nPairs;

    public static void main(String[] aa) {
        new Test4_BoundedBuffer(10, 10, 100000).test();
        pool.shutdown();
    }

    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    Test4_BoundedBuffer(int capacity, int nPairs, int nTrials) {
        this.cc = new BoundedBuffer<>(capacity);
        this.nTrials = nTrials;
        this.nPairs = nPairs;
        this.barrier = new CyclicBarrier(nPairs * 2 + 1);
    }

    void test() {
        for (int i = 0; i < nPairs; i++) {
            pool.execute(new Producer());
            pool.execute(new Consumer());
        }
        try {
            barrier.await();            // 等待所有线程就绪
            barrier.await();            // 等待所有线程执行完成
            Utils.println(putSum.get() == takeSum.get());       // 必须相等
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                int seed = this.hashCode() ^ (int) System.nanoTime();
                int sum = 0;
                barrier.await();        // 开始前等等
                for (int i = nTrials; i > 0; --i) {
                    cc.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                Utils.println("wait: " + barrier.getNumberWaiting());
                barrier.await();        // 任务完成后等带
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += cc.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


    private static class BoundedBuffer<E> {
        private final Semaphore availableItems;     // 默认0，没有许可
        private final Semaphore availableSpaces;

        private final E[] items;
        private int putPostion = 0;
        private int takePosition = 0;

        public BoundedBuffer(int capacity) {
            availableItems = new Semaphore(0);
            availableSpaces = new Semaphore(capacity);
            items = (E[]) new Object[capacity];
        }

        public boolean isEmpty() {
            return availableItems.availablePermits() == 0;
        }

        public boolean isFull() {
            return availableSpaces.availablePermits() == 0;
        }

        void put(E e) throws InterruptedException {
            availableSpaces.acquire();      // 消费一个许可
//            Utils.println("===> " + availableSpaces.availablePermits());
            doInsert(e);
            availableItems.release();       // 创建一个许可
//            Utils.println("===> " + availableItems.availablePermits());
        }

        E take() throws InterruptedException {
            availableItems.acquire();
            E e = doExtract();
            availableSpaces.release();
            return e;
        }

        private synchronized E doExtract() {
            int i = takePosition;
            E e = items[i];
            items[i] = null;
            takePosition = (++i == items.length) ? 0 : i;
            return e;
        }

        private synchronized void doInsert(E e) {
            int i = putPostion;
            items[i] = e;
            putPostion = (++i == items.length) ? 0 : i;
        }
    }
}
