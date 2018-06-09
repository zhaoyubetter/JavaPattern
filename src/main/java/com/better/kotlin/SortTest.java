package com.better.kotlin;

import com.better.Utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SortTest {
    class Dog {
        public int age;
        public String name;

        public Dog(int age, String name) {
            super();
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Dog [age=" + age + ", name=" + name + "]";
        }
    }


    /**
     * 获取今年已过月份的字符串形式：如：2018-02，2018-01
     *
     * @return
     */
    public static List<String> getThisYearCostedMonths() {
        final List<String> months = new ArrayList<>();
        final Calendar calendar = Calendar.getInstance();
        Utils.println(new SimpleDateFormat("yyyy-MM").format(new Date(calendar.getTimeInMillis())));
//        calendar.set(Calendar.MONTH, 11);
        final int year = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        if (currentMonth > 0 && currentMonth <= 12) {
            while (currentMonth >= 1) {
                months.add("" + year + "" + (currentMonth < 10 ? ("0" + currentMonth) : currentMonth));
                currentMonth--;
            }
        }
        return months;
    }

    public static void main(String[] args) {

        Utils.println(getThisYearCostedMonths());


        List<Dog> list = new ArrayList<>();
        list.add(new SortTest().new Dog(5, "DogA"));
        list.add(new SortTest().new Dog(20, "DogB"));
        list.add(new SortTest().new Dog(7, "DogC"));
        Collections.sort(list, new Comparator<Dog>() {

            @Override
            public int compare(Dog o1, Dog o2) {
                return o1.age - o2.age;
            }
        });
        System.out.println("给狗狗按照年龄倒序：" + list);
        Collections.sort(list, new Comparator<Dog>() {

            @Override
            public int compare(Dog o1, Dog o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        System.out.println("给狗狗按名字字母顺序排序：" + list);
    }
}