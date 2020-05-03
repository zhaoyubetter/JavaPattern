package com.better.newFeature.java8;

import com.better.Utils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * Java 8 Datetime handle
 */
public class Test6_Time {


    @Test
    public void test1() {
// 获取当前的日期时间
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("当前时间: " + currentTime);

        LocalDate date1 = currentTime.toLocalDate();
        System.out.println("date1: " + date1);
        Utils.println(currentTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        Month month = currentTime.getMonth();
        int day = currentTime.getDayOfMonth();
        int seconds = currentTime.getSecond();

        System.out.println("月: " + month +", 日: " + day +", 秒: " + seconds);
    }

}
