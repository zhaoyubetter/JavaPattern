package com.better.groovy.base.string

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * groovy 正则
 * 使用 ~'regex' 定义正则
 * Created by zhaoyu on 2017/4/17.
 */
def regex = ~'better'       // 创建正则，将字符串编译成 Pattern
def match = 'zhaoyubetter' =~ 'better'  // 将操作符左边的字符串跟右边的Pattern进行局部匹配，返回为Mather
def match1 = 'zhaoyubetter' =~ regex

println(match.find())   // true
println(match1.find())   // true

// === 精确匹配 ==~（左边整个字符串与右边的模式） ==
println('zhaoyubetter' ==~ 'better')   // false

// ===== 支持 // 来定义正则 =====
regex = ~/\w+@\w+(\.\w+)+/
println(('zhaoyubetter@126.com' =~ regex).find())   // true


println("====================")
def pattern = ~/.*\/(.+)/   // //

def str1 = "@+id/button1"
def str3 = "@id/button3"
def str2 = "@android:id/button2"


def matcher = str3 =~ pattern
println(matcher[0][1])


println("----- 分组获取 -----")
pattern = ~/@((\+id)|(id)|(android:id))\/(.*)/
matcher = str1 =~ pattern
println("分类1：${matcher[0][2]} ${matcher[0][3]} ${matcher[0][4]} ${matcher[0][5]}")
matcher = str2 =~ pattern
println("分类2：${matcher[0][2]} ${matcher[0][3]} ${matcher[0][4]} ${matcher[0][5]}")
matcher = str3 =~ pattern
println("分类3：${matcher[0][2]} ${matcher[0][3]} ${matcher[0][4]} ${matcher[0][5]}")

/*
println('============ java =============')
Pattern tPattern = Pattern.compile("(\\d{3,5})([a-z]{2})");
String tStr4 = "123bd-3434dc-34333dd-00";
Matcher tMatcher = tPattern.matcher(tStr4);
while (tMatcher.find()) {
    println(tMatcher.group(0));    //  字符串本身    (\d{3,5})([a-z]{2}) 输出： 123bd  3434dc 34333dd
    println(tMatcher.group(1));   // 第一组     (d{3,5})         输出： 123 3434 34333
    println(tMatcher.group(2));   // 第二组  ([a-z]{2})          输出： bd  dc dd
}
*/


/*
String text = "John writes about this, and John Doe writes about that, and John Wayne writes about everything.";
String patternString1 = "((John) (.+?)) ";  // 注意，这里有个空格
//String patternString1 = "John .+? ";
pattern = Pattern.compile(patternString1);
matcher = pattern.matcher(text);
while (matcher.find()) {
//    System.out.println("found: <" + matcher.group(1) + "> <" + matcher.group(2) + "> <" + matcher.group(3) + ">");
    System.out.println("found: <" + matcher.group(1) + ">");
}
*/


