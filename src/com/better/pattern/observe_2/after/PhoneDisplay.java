package com.better.pattern.observe_2.after;

/**
 * Created by zhaoyu on 2016/12/12.
 */
public class PhoneDisplay extends Observer {

    private Subject subject;

    public PhoneDisplay(Subject subject) {
        name = "手机";
        this.subject = subject;
        this.subject.addObserver(this);
    }

    @Override
    public void update(int pressure, int fret) {
        msg = "【" + name + "】" + String.format("当前气压：%s, 轮胎磨损度：%s", pressure, fret);
        System.out.println(msg);
    }
}
