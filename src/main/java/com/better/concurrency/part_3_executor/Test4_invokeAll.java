package com.better.concurrency.part_3_executor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * https://blog.csdn.net/w1014074794/article/details/51098746
 */
public class Test4_invokeAll {


    // 固定大小的线程池，同时只能接受5个任务
    static ExecutorService mExecutor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        try {
            Test4_invokeAll it = new Test4_invokeAll();
            it.getRankedTravelQuotes();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算价格的任务
     *
     * @author hadoop
     */
    private class QuoteTask implements Callable<BigDecimal> {
        public final double price;
        public final int num;

        public QuoteTask(double price, int num) {
            this.price = price;
            this.num = num;
        }

        @Override
        public BigDecimal call() throws Exception {
            Random r = new Random();
            long time = (r.nextInt(10) + 1) * 1000;
            Thread.sleep(time);

            BigDecimal d = BigDecimal.valueOf(price * num).setScale(2);
            System.out.println("耗时：" + time / 1000 + "s,单价是：" + price + ",人数是："
                    + num + "，总额是：" + d);
            return d;
        }
    }

    /**
     * 在预定时间内请求获得旅游报价信息
     *
     * @return
     */
    public void getRankedTravelQuotes() throws InterruptedException {
        List<QuoteTask> tasks = new ArrayList<>();
        // 模拟10个计算旅游报价的任务
        for (int i = 1; i <= 20; i++) {
            tasks.add(new QuoteTask(200, i));
        }

        /**
         * 使用invokeAll方法批量提交限时任务任务 预期15s所有任务都执行完,没有执行完的任务会自动取消
         *
         */
        List<Future<BigDecimal>> futures = mExecutor.invokeAll(tasks, 15, TimeUnit.SECONDS);
        // 报价合计集合
        List<BigDecimal> totalPriceList = new ArrayList<>();

        // 顺序对应
        Iterator<QuoteTask> taskIter = tasks.iterator();

        for (Future<BigDecimal> future : futures) {
            QuoteTask task = taskIter.next();
            try {
                totalPriceList.add(future.get());
            } catch (ExecutionException e) {
                // 返回计算失败的原因
                // totalPriceList.insertLast(task.getFailureQuote(e.getCause()));
                totalPriceList.add(BigDecimal.valueOf(-1));
                System.out.println("任务执行异常,单价是" + task.price + "，人数是：" + task.num);
            } catch (CancellationException e) {
                // totalPriceList.insertLast(task.getTimeoutQuote(e));
                totalPriceList.add(BigDecimal.ZERO);
                System.out.println("任务超时，取消计算,单价是" + task.price + "，人数是：" + task.num);
                future.cancel(true);
            }
        }
        for (BigDecimal bigDecimal : totalPriceList) {
            System.out.println(bigDecimal);
        }
        mExecutor.shutdown();
    }

}
