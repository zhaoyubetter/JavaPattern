package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BlockQueue
 */
public class Test4_blockingQueue_1 {

    public static void main(String... aa) throws InterruptedException {
        // 容量为 10
        BlockingQueue<String> queue = new LinkedBlockingDeque<>(10);

        // 生成者
        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);

        // 消费者
        Consumer consumer1 = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);

        // 借助Executors
        ExecutorService service = Executors.newCachedThreadPool();

        // 启动线程
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer1);
        service.execute(consumer2);

        // 执行10s
        Thread.sleep(10 * 1000);

        // 生产者
        producer1.stop();
        producer2.stop();
        producer3.stop();

        // 消费者
        consumer1.stop();
        consumer2.stop();

        Thread.sleep(2000);
        // 退出Executor
        service.shutdown();
    }

    static class Producer implements Runnable {
        private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;
        private static AtomicInteger count = new AtomicInteger();
        private volatile boolean isRunning = true;
        private BlockingQueue queue;

        public Producer(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            String data = null;
            Random r = new Random();
            Utils.println("启动生产者线程！");

            try {
                while (isRunning) {
                    Thread.sleep(DEFAULT_RANGE_FOR_SLEEP);
                    data = "data: " + count.incrementAndGet();
                    Utils.println(">>>>>将数据：" + data + " 放入队列。。。");
                    // 放入数据
                    if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                        Utils.println("放入数据失败：" + data);
                    }
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            } finally {
                Utils.println("退出生产者线程！");
            }
        }

        public void stop() {
            isRunning = false;
        }
    }

    static class Consumer implements Runnable {

        private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;
        private BlockingQueue<String> queue;
        private volatile boolean isRunning = true;

        public Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            Utils.println("启动消费者线程！");
            Random r = new Random();
            try {
                while (isRunning) {
                    Utils.println("从队列获取数据...");
                    String data = queue.poll(2, TimeUnit.SECONDS);
                    if (null != data) {
                        Utils.println("<<<<<拿到数据: " + data + "，并消费她...");
                        Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
                    } else {
                        // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
                        isRunning = false;
                    }
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            } finally {
                Utils.println("退出消费者线程！");
            }
        }

        public void stop() {
            isRunning = false;
        }
    }
}
