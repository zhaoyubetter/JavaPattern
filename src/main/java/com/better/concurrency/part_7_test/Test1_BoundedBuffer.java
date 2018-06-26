package com.better.concurrency.part_7_test;

import com.better.Utils;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 缓存的有界与阻塞
 *
 * @param
 */
public class Test1_BoundedBuffer {
    public static void main(String[] aa) {
        BoundedBuffer<Integer> a = new BoundedBuffer<>(5);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        Utils.println(a.take());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        Utils.println(Thread.currentThread().getName() + " " + a.take());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        Thread.sleep(300);
                        a.put(new Random().nextInt(300));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
            Utils.println("===> " + availableSpaces.availablePermits());
            doInsert(e);
            availableItems.release();       // 创建一个许可
            Utils.println("===> " + availableItems.availablePermits());
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
