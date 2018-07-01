package com.better.concurrency.part_8_lock;

import com.better.Utils;

import java.util.Random;

/**
 * 通过轮询与休眠来实现简单的阻塞
 * 无限死循环。定期调用
 */
public class Test2_BoundedBuffer {

    public static void main(String[] args) throws InterruptedException {
        SleepyBoundedBuffer boundedBuffer = new SleepyBoundedBuffer<String>(10);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        boundedBuffer.put("a");
                        Utils.println("put ----> ");
                        Thread.sleep(new Random().nextInt(1000));
                        break;
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
                    while (true) {
                        Thread.sleep(new Random().nextInt(1000));
                        Utils.println("take ----> " + boundedBuffer.take());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private static class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
        protected SleepyBoundedBuffer(int capacity) {
            super(capacity);
        }

        public void put(V v) throws InterruptedException {
            while (true) {
                synchronized (this) {
                    if (!isFull()) {
                        doPut(v);
                        return;
                    }
                }

                Thread.sleep(3000);
            }
        }

        public V take() throws InterruptedException {
            while (true) {
                Utils.println("take() called()");       //
                synchronized (this) {
                    if (!isEmpty()) {
                        return doTake();
                    }
                }

                Thread.sleep(3000);
            }
        }

    }


}



