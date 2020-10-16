package com.better.pattern.chain;

public class ClientMain {
    public static void main(String... a) {
        Handler zhangsan = new DirectorHandler("张三");
        Handler lisi = new ManagerHandler("李四");
        Handler wangwu = new TopManagerHandler("王五");

        // 创建责任链
        zhangsan.nextHandler = (lisi);
        lisi.nextHandler = (wangwu);

        
    }
}