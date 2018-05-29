package com.better.concurrency.part_2_base;


import com.better.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 简单的文件遍历
 */
public class Test5_blockingQueue_2 {

    public static void main(String... aa) throws InterruptedException {
        final FileFilter xmlFile = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("AndroidManifest.xml");
            }
        };

        // 查找结果
        final List<File> results = Collections.synchronizedList(new ArrayList<>());

        // 根目录
        final File rootPath = new File("/Users/zhaoyu1/");
        final File[] roots = rootPath.listFiles();

        final BlockingQueue<File> queue = new LinkedBlockingQueue<>(10);

        // ==== 生产者
        for (File root : roots) {
            new Thread(new FileCrawler(queue, xmlFile, root)).start();
        }

        // ==== 消费者
        for (int i = 0; i < 5; i++) {
            new Thread(new Indexer(queue, results)).start();
        }
    }

    static class FileCrawler implements Runnable {
        private final BlockingQueue<File> fileQueue;
        private final FileFilter fileFilter;
        private final File root;

        public FileCrawler(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {
            this.fileQueue = fileQueue;
            this.fileFilter = fileFilter;
            this.root = root;
        }

        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void crawl(File root) throws InterruptedException {
            final File[] files = root.listFiles();
            if (files != null) {
                for (File entry : files) {
                    if (entry.isDirectory()) {
                        crawl(entry);
                    } else {
                        if (entry.getName().endsWith("AndroidManifest.xml")) {
                            fileQueue.put(entry);
                        }
                    }
                }
            }
        }
    } // end class FileCrawler

    // 消费者，分析文件
    static class Indexer implements Runnable {

        private final BlockingQueue<File> queue;
        private final List<File> list;

        public Indexer(BlockingQueue<File> queue, List<File> list) {
            this.queue = queue;
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    File file = queue.poll(2, TimeUnit.SECONDS);
                    if(file != null) {
                        indexFile(file);
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void indexFile(File file) throws IOException {
            Utils.println("Thread: " + Thread.currentThread().getName() + ", 处理文件：" + file.getAbsolutePath());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.contains("android.permission.WRITE_EXTERNAL_STORAGE")) {
                    Utils.println("found one ");
                    list.add(file);
                }
            }
        }
    }
}
