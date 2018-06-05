package com.better.concurrency.part_3_executor;

import com.better.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 模拟html渲染 （文字与图片）
 * 1. 文字与图片 渲染 是 并行；
 * 2. 图片下载后，立即渲染，为每个图片的下载创建一个任务
 * 3. 线程越多，越快
 */
public class Test3_completion_service_2 {

    public static void main(String[] args) throws InterruptedException {
        new FutureRender().render();
    }

    private static class FutureRender {
        private final ExecutorService executorService = Executors.newFixedThreadPool(10);

        void render() {
            long startTime = System.currentTimeMillis();

            final List<ImageInfo> imageInfos = getImageInfo();
            final CompletionService<ImageData> completionService =
                    new ExecutorCompletionService<>(executorService);

            // 来一个，创建一个任务；
            for(final ImageInfo info : imageInfos) {
                completionService.submit(new Callable<ImageData>() {
                    @Override
                    public ImageData call() throws Exception {
                        return info.dowImage();
                    }
                });
            }

            // 2.模拟渲染文字文件
            renderText();

            // 1.模拟图片下载
            try {
                for(final ImageInfo info : imageInfos) {
                    final Future<ImageData> future = completionService.take();
                    future.get().render();
                }
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                Utils.println("执行耗时：" + (System.currentTimeMillis() - startTime) + "ms");  // 相当快
            }
        }


        private void renderText() {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(800);
                        Utils.println("render text");       // 总耗时 小于 预估的 4800ms
                    } catch (InterruptedException e) {
                    }
                }
            });
        }



        private List<ImageInfo> getImageInfo() {
            List<ImageInfo> imageInfos = new ArrayList<>(20);
            for (int i = 0; i < 20; i++) {
                imageInfos.add(new ImageInfo("ImageInfo: " + i));
            }
            return imageInfos;
        }
    }

    private static class ImageInfo {
        String name;
        String url;

        public ImageInfo(String name) {
            this.name = name;
        }

        ImageData dowImage() throws InterruptedException {
            Thread.sleep(200);  // 模拟下载
            return new ImageData(name);
        }
    }

    /**
     * 图片数据
     */
    private static class ImageData {
        String name;

        public ImageData(String name) {
            this.name = name;
        }

        void render() {
            Utils.println("ImageData ===> render :" + name);
        }
    }

}
