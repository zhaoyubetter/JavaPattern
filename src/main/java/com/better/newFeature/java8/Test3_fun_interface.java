package com.better.newFeature.java8;

import com.better.Utils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

/**
 * 函数式接口 @FunctionInterface
 * 1. 有且仅有一个抽象方法，可以有多个非抽象方法
 * 2. 接口添加 @FunctionalInterface 注解
 * <p>
 * java.util.function
 */
public class Test3_fun_interface {

    /**
     * Predicate
     * 接口是一个函数式接口，它接受一个输入参数 T，返回一个布尔值结果
     */
    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        eval(list, n -> true);   // predicate

        // println even
        Utils.println("================ even ===========");
        eval(list, n -> n % 2 == 0);
    }

    /**
     * Function 接口
     * Function 接口有一个参数并且返回一个结果，并附带了一些可以和其他函数组合的默认方法（compose, andThen）：
     */
    @Test
    public void test2() {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        backToString.apply("4534535");
    }

    /**
     * Supplier 接口
     * Supplier 接口返回一个任意范型的值，和Function接口不同的是该接口没有任何参数
     */
    @Test
    public void test3() {
        Supplier<String> supplier = String::new;
        Utils.println(supplier.get());
    }

    /**
     * Consumer
     * Consumer 接口表示执行在单个参数上的操作
     */
    @Test
    public void test4() {
        Consumer<String> greeter = p -> System.out.println("Hello, " + p);
        greeter.accept(new String("better"));
    }

    private void eval(List<Integer> list, Predicate<Integer> predicate) {
        for (Integer n : list) {
            if (predicate.test(n)) {
                System.out.println(n + " ");
            }
        }
    }

}
