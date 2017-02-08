package com.better.sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ，将一个字符串中的空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 * Created by zhaoyu1 on 2016/11/4.
 */
public class MyClass2 {
    public static void main(String[] args) {
        System.out.println(replaceSpace(new StringBuffer("We are Happy")));
    }

    public static String replaceSpace(StringBuffer str) {
        String result = null;
        if (str != null && str.length() > 0) {
            Pattern pattern = Pattern.compile("(\\s)");
            Matcher matcher = pattern.matcher(str);
            result = matcher.replaceAll("%20");
        }
        return result;
    }
}
