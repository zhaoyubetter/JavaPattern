package com.better.concurrency.part_4_cancel;

import com.better.Utils;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 通过中断修复
 */
public class Test2_blockingqueue {


    public static void main(String... a) throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
        BrokenProducer producer = new BrokenProducer(queue);
        producer.start();

        Thread.sleep(3000);

        // 消费
        Utils.println(queue.take());

        producer.cancel();

    }

    private static class BrokenProducer extends Thread {

        private final BlockingQueue<Integer> queue;

        public BrokenProducer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    int num = new Random().nextInt(300);
                    Thread.currentThread().sleep(num);
                    Utils.println("放入数据：" + num);
                    queue.put(num);             // 消费者，停止消费，这里一直阻塞，无法从阻塞中恢复出来，检查不到  cancelled 状态、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
                }
                Utils.println("cancel");
            } catch (InterruptedException e) {
                /*allow program exit*/
            }
        }

        void cancel() {
            interrupt();
        }
    }

}
