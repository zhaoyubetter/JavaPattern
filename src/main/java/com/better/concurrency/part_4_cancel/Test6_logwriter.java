package com.better.concurrency.part_4_cancel;

import com.better.Utils;
import sun.rmi.runtime.Log;

import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 不支持关闭的生产者, 也就是JVM无法关闭；
 * 日志服务运行在单独的thread中
 */
public class Test6_logwriter {
    public static void main(String[] args) throws InterruptedException {
        final LogWriter log = new LogWriter();
        log.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.log("Hello: " + new Random().nextInt());
                    Thread.sleep(200);
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
    }

    private static class LogWriter {
        private final BlockingQueue<String> queue;
        private final LoggerThread logger;

        public LogWriter() {
            this.queue = new LinkedBlockingQueue<>(5);
            this.logger = new LoggerThread();
        }

        public void start() {
            logger.start();
        }

        public void log(String msg) throws InterruptedException {
            queue.put(msg);
        }

        private class LoggerThread extends Thread {
            private final PrintWriter writer = new PrintWriter(System.out);

            @Override
            public void run() {
                try {
                    while (true) {
                        writer.println(queue.take());
                        writer.flush();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    writer.close();
                }
            }
        }
    }


}
