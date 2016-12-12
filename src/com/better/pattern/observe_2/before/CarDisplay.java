package com.better.pattern.observe_2.before;

/**
 * Created by zhaoyu on 2016/12/11.
 */
public class CarDisplay {

    private String name = "车载显示屏";
    private String msg;

    /**
     * @param pressure 压力
     * @param fret     磨损度
     */
    public void update(int pressure, int fret) {
        msg = "【" + name + "】" + String.format("当前气压：%s, 轮胎磨损度：%s", pressure, fret);
        System.out.println(msg);
    }

    public String getMsg() {
        return msg;
    }
}
