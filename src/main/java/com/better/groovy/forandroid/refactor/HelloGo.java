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
        String sourcePath = "/Users/zhaoyu1/Documents/github/AndroidResourceTools/app/src/main/java";
        String resPath = "/Users/zhaoyu1/Documents/github/AndroidResourceTools/app/src/main/res";
        String manifestFilePath = "/Users/zhaoyu1/Documents/github/AndroidResourceTools/app/src/main/AndroidManifest.xml";

        final ResToolsConfig config = new ResToolsConfig("app_main_", "better_", sourcePath, resPath, manifestFilePath);



//        // 1.test layout
        LayoutReplace layoutReplace = new LayoutReplace(config);
        layoutReplace.replaceThis();

        // 2.test drawable
        DrawableReplace drawableReplace = new DrawableReplace(config);
        drawableReplace.replaceThis();
//
//
//        // 3. test color

        ColorReplace colorReplace = new ColorReplace(config);
        colorReplace.replaceThis();
//
//
//        // 4. test Anim
        AnimReplace anim = new AnimReplace(config);
        anim.replaceThis();
//
//        // 5. test menu
        MenuReplace menuReplace = new MenuReplace(config);
        menuReplace.replaceThis();
//
//        // 6. test mipmap   == need test more time
        MipmapReplace mipmapReplace = new MipmapReplace(config);
        mipmapReplace.replaceThis();
//
//        // 7.test raw
        RawReplace rawReplace = new RawReplace(config);
        rawReplace.replaceThis();
//
//        // 8.test xml
        XmlReplace xmlReplace = new XmlReplace(config);
        xmlReplace.replaceThis();


        // ===9.values test not support attrs
        ValuesReplace valuesReplace = new ValuesReplace(config);
        Set<ValuesReplace.ValuesType> set = new HashSet<>();
        set.add(ValuesReplace.ValuesType.string);
        valuesReplace.replaceValues(ValuesReplace.ALL_VALUES_TYPES);


//        testString();

    }


    private static void testString() {
        String str = " import android.support.v7.app.AppCompatActivity\n" +
                "import android.os.Bundle\n" +
                "import kotlinx.android.synthetic.main.better_activity_main.*";

        HashSet<String> set = new HashSet<>();
        handleSrcFile(str, set, "(main\\.)(\\w+)(\\.)");
    }

    private static void handleSrcFile(String text, Set<String> set, String regex) {
        String fileContent = text;                      // every extend is a text extend
        StringBuffer sb = new StringBuffer();            // result content

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(fileContent);
        while (matcher.find()) {
            String oldResName = matcher.group(2);          // the old res name
            System.out.println(oldResName);
            String newResName = "aaa" + oldResName;
//            if(1==1) {
//                matcher.appendReplacement(sb, "\\$1" + newResName); // 拼接 保留$1分组,替换$6分组
//            } else {
//                matcher.find();
//            }

        }
        // 修改了文件时，才写入文件
        if (sb.length() > 0) {
            matcher.appendTail(sb);              // 添加结尾
            System.out.println(sb);
        }
    }
}
