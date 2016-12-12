package com.better.pattern.observe_2.repair;

/**
 * Created by zhaoyu on 2016/12/12.
 */
public class CarDisplay extends Observer {

    public CarDisplay() {
        name = "车载显示屏";
    }

    @Override
    public void update(int pressure, int fret) {
        msg = "【" + name + "】" + String.format("当前气压：%s, 轮胎磨损度：%s", pressure, fret);
        System.out.println(msg);
    }
}
