package com.better.concurrency.part_3_executor;

import com.better.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 模拟html渲染 （文字与图片）
 * 1. 文字与图片 渲染 是 并行；
 * 2. 但是图片其实是串行线程；
 */
public class Test3_completion_service_1 {

    public static void main(String[] args) throws InterruptedException {
        new FutureRender().render();
    }

    private static class FutureRender {
        private final ExecutorService executorService = Executors.newFixedThreadPool(4);

        void render() {
            long startTime = System.currentTimeMillis();

            final List<ImageInfo> imageInfos = getImageInfo();

            Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
                @Override
                public List<ImageData> call() throws Exception {
                    final List<ImageData> result = new ArrayList<>();
                    for (ImageInfo info : imageInfos) {
                        result.add(info.dowImage());        // 模拟下载，这里实际上，是串行
                    }
                    return result;
                }
            };

            // 2.模拟渲染文字文件
            renderText();
            // 1.模拟下载图片任务
            Future<List<ImageData>> future = executorService.submit(task);


            try {
                // 下载完成后，整体去渲染
                final List<ImageData> imageDatas = future.get();
                for (ImageData imageData : imageDatas) {
                    imageData.render();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                // 重新设置中断状态
                Thread.currentThread().interrupt();
                future.cancel(true);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                Utils.println("执行耗时：" + (System.currentTimeMillis() - startTime) + "ms");  // 总耗时 小于 4800ms
            }
        }


        private void renderText() {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(800);
                        Utils.println("render text");
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
