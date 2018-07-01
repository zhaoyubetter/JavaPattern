package com.better.concurrency.part_8_lock;

import com.better.Utils;

import java.util.Random;

/**
 * 通过条件队列实现的有界缓存，
 * 优化通知 ===》条件通知
 */
public class Test4_BoundedBuffer {

    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer boundedBuffer = new BoundedBuffer<String>(10);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        boundedBuffer.put("a");
                        Utils.println("put ----> ");
                        Thread.sleep(3000);

                        boundedBuffer.put("b");
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

    private static class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
        // 条件谓词：not-full ( !isFull() )
        protected BoundedBuffer(int capacity) {
            super(capacity);
        }

        // 阻塞，直到 not-full
        public synchronized void put(V v) throws InterruptedException {
            while (isFull()) {
                wait();     // 等待
            }
            boolean wasEmpty = isEmpty();
            doPut(v);
            if(wasEmpty) {      // 影响到状态时，才发出通知
                notifyAll();    // 唤醒所有等待线程
            }
        }

        // 阻塞，直到 not-empty
        public synchronized V take() throws InterruptedException {
            while (isEmpty()) {
                Utils.println("take() called()");       // 阻塞了，没有一直调用
                wait();
            }
            V v = doTake();
            notifyAll();
            return v;
        }
    }

}



