package com.better.pattern.observe_2.before;

/**
 * Created by zhaoyu on 2016/12/11.
 */
public class PhoneDisplay {

    private String name = "手机";
    private String msg;

    public void update(int pressure, int fret) {
        msg = "【" + name + "】" + String.format("当前气压：%s, 轮胎磨损度：%s", pressure, fret);
        System.out.println("【" + name + "】" + String.format("当前气压：%s, 轮胎磨损度：%s", pressure, fret));
    }

    public String getMsg() {
        return msg;
    }
}

