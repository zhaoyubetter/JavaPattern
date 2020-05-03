package com.better.newFeature.java8;

import com.better.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * 方法引用
 * https://www.runoob.com/java/java8-method-references.html
 */
public class Test2_method_ref {

    /**
     * 构造器引用
     */
    @Test
    public void test1() {
        // 构造器引用, 语法 Class:new
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);
        Utils.println(car.age);
    }

    /**
     * 静态方法引用
     * 语法是Class::static_method
     */
    @Test
    public void test2() {
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);
        cars.forEach(Car::collide);
    }

    /**
     * 成员方法引用
     * Class::method
     */
    @Test
    public void test3() {
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);
        cars.forEach(Car::repair);
    }

    /**
     * 特定对象方法
     * instance::method
     */
    @Test
    public void test4() {
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);
        cars.forEach(car::follow);
    }

    @Test
    public void test5() {
        List names = new ArrayList();

        names.add("Google");
        names.add("Runoob");
        names.add("Taobao");
        names.add("Baidu");
        names.add("Sina");

        names.forEach(System.out::println);
    }

    static class Car {

        int age = 5;

        static Car create(final Supplier<Car> supplier) {
            return supplier.get();
        }

        static void collide(final Car car) {
            Utils.println("Collided " + car.age);
        }

        void follow(final Car another) {
            Utils.println("Following the " + another);
        }

        void repair() {
            Utils.println("Repaired " + this);
        }
    }
}

