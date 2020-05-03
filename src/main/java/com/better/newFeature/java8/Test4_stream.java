package com.better.newFeature.java8;

import com.better.Utils;
import org.junit.Test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Test4_stream {

    /**
     * 生成流
     * 1. stream() 为集合创建流
     */
    @Test
    public void test() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        filtered.forEach(it -> Utils.println(it));
    }

    /**
     * forEach
     */
    @Test
    public void test2() {
        Random random = new Random(10);
        random.ints().limit(10).forEach(System.out::println);
    }

    /**
     * 测试并行 parallel
     * 它通过默认的ForkJoinPool,可能提高你的多线程任务的速度
     */
    @Test
    public void testParallel() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        // 获取空字符串的数量
        long count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        Utils.println(count);
    }

    /**
     * Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：
     */
    @Test
    public void testCollectors() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        System.out.println("筛选列表: " + filtered);
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);
    }

    /**
     * IntSummaryStatistics 统计
     */
    @Test
    public void testStat() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        IntSummaryStatistics stats = numbers.stream().mapToInt(it -> it).summaryStatistics();
        Utils.println("max:" + stats.getMax());
        Utils.println("min:" + stats.getMin());
    }

}
