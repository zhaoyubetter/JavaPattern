package com.better.regex;

import com.better.Utils;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 {
    @Test
    public void test1() {
        // ([\\w-]+):\\s+([\\w-]+);\\s+(\\w+)=\"([^\"]+)\"(;\\s+(\\w+)=\"([^\"]+)\")?")
        String string = "Content-Disposition: form-data; name=\"file1\"; filename=\"XmlReader.java\"";

        // [^;] 表示 ; 之外的所有字符
        String str = "Content-Disposition: form-data; name=\"cccc\"";
        Pattern pattern = Pattern.compile("([\\w-]+):\\s([\\w-]+);\\s(\\w+)=\"([^\"]+)\"(;\\s+(\\w+)=\"([^\"]+)\")?");
        Matcher matcher = pattern.matcher(string);
        if(matcher.find()) {
            Utils.println(matcher.group(1));
            Utils.println(matcher.group(2));
            Utils.println(matcher.group(3));
            Utils.println(matcher.group(4));
            Utils.println(matcher.group(5));
            Utils.println(matcher.group(6));
            Utils.println(matcher.group(7));
        }
    }

    @Test
    public void test2() {
        String str = "Content-Type: application/vnd.openxmlformats-officedocument.presentationml.presentation; aa";
        Pattern pattern = Pattern.compile("([\\w-]+):\\s([^;]+);?\\s?((.+))?");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()) {
            Utils.println(matcher.group(1));
            Utils.println(matcher.group(2));
            Utils.println(matcher.group(3));
        }
    }
}
