package com.better.concurrency.part_4_cancel;

import com.better.Utils;
import com.better.concurrency.anno.GuardedBy;

import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 日志线程响应中断，后关闭
 */
public class Test6_logwriter_cancel {
    public static void main(String[] args) throws InterruptedException {
        final LogWriter log = new LogWriter();
        log.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.log("Hello: " + new Random().nextInt());
                    Thread.sleep(800);
                    log.log("----> Kotlin");
                } catch (InterruptedException e1) {
                    Utils.println("中断了 ：  " + e1);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.log(Thread.currentThread().getName() + "---> log");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();

        log.log("hhhhh");
        log.log("aaa");
        log.log("bb");
        log.log("cc");
        log.stop();
    }

    private static class LogWriter {
        private final BlockingQueue<String> queue;
        private final LoggerThread logger;
        @GuardedBy("this")
        private boolean isShutDown;
        @GuardedBy("this")
        private int reservations;

        public LogWriter() {
            this.queue = new LinkedBlockingQueue<>(3);
            this.logger = new LoggerThread();
        }

        public void start() {
            logger.start();
        }

        public void stop() {
            synchronized (this) {
                isShutDown = true;
                logger.interrupt();
            }
        }

        public void log(String msg) throws InterruptedException {
            synchronized (this) {
                if (isShutDown) {
                    Utils.println("<<<日志线程已关闭！");
                    throw new IllegalStateException("日志线程已关闭！");
                }
                ++reservations;
            }
            queue.put(msg);
        }

        private class LoggerThread extends Thread {
            private final PrintWriter writer = new PrintWriter(System.out);

            @Override
            public void run() {
                try {
                    while (true) {
                        try {
                            synchronized (LogWriter.this) {
                                if (isShutDown && reservations == 0) {
                                    break;      // 退出while
                                }
                            }
                            final String message = queue.take();
                            synchronized (LogWriter.this) {
                                reservations--;
                                writer.println(message);
                                writer.flush();
                            }
                        } catch (InterruptedException e) {
                            Utils.println("中断捕获");
                        }
                    }
                } finally {
                    writer.close();
                }
            }
        }
    }


}
