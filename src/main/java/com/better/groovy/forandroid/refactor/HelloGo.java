package com.better.groovy.forandroid.refactor;


import com.better.groovy.forandroid.refactor.folder.*;
import com.better.groovy.forandroid.refactor.values.ValuesReplace;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloGo {

    public static void main(String[] args) throws IOException {
        String sourcePath = "/Users/zhaoyu1/Documents/git.jd/jdme_android/app/src/main/java";
        String resPath = "/Users/zhaoyu1/Documents/git.jd/jdme_android/app/src/main/res";

//        // 1.test layout
        LayoutReplace layoutReplace = new LayoutReplace(sourcePath, resPath);
        layoutReplace.replaceThis();
//
//        // 2.test drawable
        DrawableReplace drawableReplace = new DrawableReplace(sourcePath, resPath);
        drawableReplace.replaceThis();
//
//
//        // 3. test color
        ColorReplace colorReplace = new ColorReplace(sourcePath, resPath);
        colorReplace.replaceThis();
//
//
//        // 4. test Anim
        AnimReplace anim = new AnimReplace(sourcePath, resPath);
        anim.replaceThis();
//
//        // 5. test menu
        MenuReplace menuReplace = new MenuReplace(sourcePath, resPath);
        menuReplace.replaceThis();
//
//        // 6. test mipmap   == need test more time
        MipmapReplace mipmapReplace = new MipmapReplace(sourcePath, resPath);
        mipmapReplace.replaceThis();
//
//        // 7.test raw
        RawReplace rawReplace = new RawReplace(sourcePath, resPath);
        rawReplace.replaceThis();
//
//        // 8.test xml
        XmlReplace xmlReplace = new XmlReplace(sourcePath, resPath);
        xmlReplace.replaceThis();


        // ===9.values test not support attrs
        ValuesReplace valuesReplace = new ValuesReplace(sourcePath, resPath);
        Set<ValuesReplace.ValuesType> set = new HashSet<>();
        set.add(ValuesReplace.ValuesType.string);
        valuesReplace.replaceValues(ValuesReplace.ALL_VALUES_TYPES);


        testString();

    }


    private static void testString() {
//        String str = "<string name=\"me_didi_car_searching\" translatable=\"false\" formatted=\"true\"> 正在为您寻找车辆</string>";
////        String str = "<string name=\"me_didi_car_searching\"> 正在为您寻找车辆</string>";
//        String regex ="(<string\\s+name\\s*=\\s*[\\\"'])(\\w+)(\\s*.*[\\\"']\\s*>)";
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(str);
//        while(matcher.find()) {
//
//            System.out.println(matcher.group(1));
//            System.out.println(matcher.group(2));
//            System.out.println(matcher.group(3));
//        }


    }
}
