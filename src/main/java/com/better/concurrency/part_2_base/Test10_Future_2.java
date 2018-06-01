package com.better.concurrency.part_2_base;

import com.better.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * 可利用阻塞队列优化;
 * TODO:
 */
public class Test10_Future_2 {
    public static void main(String... ag) throws ExecutionException, InterruptedException {
        File file = new File("/Users/zhaoyu1/Documents/github/");
        String key = "android.permission.WRITE_EXTERNAL_STORAGE";
        FutureTask<Integer> task = new FutureTask<>(new FileCounterTask(file, key));
        new Thread(task).start();

        long currentTime = System.currentTimeMillis();
        Utils.println(task.get());
        Utils.println("cost: " + (System.currentTimeMillis() - currentTime) + "ms");
    }

    private static class FileCounterTask implements Callable<Integer> {

        int count = 0;
        File dir;
        String keyword;

        public FileCounterTask(File dir, String keyword) {
            this.dir = dir;
            this.keyword = keyword;
        }

        @Override
        public Integer call() throws Exception {
            count = 0;
            try {
                File[] files = dir.listFiles();
                List<FutureTask<Integer>> results = new ArrayList<>();

                for(File file : files) {
                    if(file.isDirectory()) {        // 目录，
                        FileCounterTask task = new FileCounterTask(file, keyword);
                        FutureTask<Integer> futureTask = new FutureTask<>(task);
                        results.add(futureTask);
                        new Thread(futureTask).start();
                    } else {
                        if(search(file))
                            count++;
                    }
                }

                // 当前目录下所有
                for(FutureTask<Integer> task: results) {
                    count += task.get();
                }

            } catch(Exception e){}

            return count;
        }

        public boolean search(File file) {
            if(!file.getName().endsWith(".xml")) {
                return false;
            }
            try {
                Scanner in = new Scanner(new FileInputStream(file));
                boolean found = false;
                while(!found && in.hasNextLine()) {
                    String line = in.nextLine();
                    if(line.contains(keyword)) {
                        System.out.println(file.getAbsolutePath());
                        found = true;
                    }
                }

                in.close();
                return found;
            } catch(IOException e) {
                return false;
            }
        }
    }
}
