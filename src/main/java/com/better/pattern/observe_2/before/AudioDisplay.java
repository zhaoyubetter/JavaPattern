package com.better.pattern.observe_2.before;

/**
 * 音响系统
 * Created by zhaoyu on 2016/12/12.
 */
public class AudioDisplay {
    private String name = "音响系统报警";
    private String msg;

    public void update(int pressure, int fret) {
        msg = "【" + name + "】" + String.format("当前气压：%s, 轮胎磨损度：%s", pressure, fret);
        System.out.println(msg);
    }

    public String getMsg() {
        return msg;
    }
}
