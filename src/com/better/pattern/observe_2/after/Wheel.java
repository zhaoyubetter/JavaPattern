package com.better.pattern.observe_2.after;

import java.util.*;

/**
 * 轮胎subject类
 * Created by zhaoyu on 2016/12/11.
 */
public class Wheel implements Subject {

    private int pressure;       // 气压
    private int fret;           // 磨损度

    public int getHeight() {
        return height;
    }

    private int height;

    private List<Observer> observers = new ArrayList<>();

    private Map<String, List<Observer>> maps = new HashMap();

    /**
     * 数据改变了，此方法将调用
     */
    public void dataUpdate() {
        notifyObservers();
    }

    public void removeObserver(Observer o) {
        if (observers.contains(o)) {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(getPressure(), getFret());
        }
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
        dataUpdate();
    }

    public int getFret() {
        return fret;
    }

    public void setFret(int fret) {
        this.fret = fret;
        dataUpdate();
    }

    public String getMsg() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < observers.size(); i++) {
            sb.append(observers.get(i).getMsg()).append("\n");
        }
        return sb.toString();
    }
}
