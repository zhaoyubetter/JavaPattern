package com.better.pattern.observe_2.before;

/**
 * 轮胎subject类
 * Created by zhaoyu on 2016/12/11.
 */
public class Wheel {

    private int pressure;       // 气压
    private int fret;           // 磨损度

    // 其他引用
    private CarDisplay car;
    private PhoneDisplay phone;
    private AudioDisplay audio;


    public Wheel(CarDisplay car, PhoneDisplay phone) {
        this.car = car;
        this.phone = phone;
    }

    public void setAudio(AudioDisplay audio) {
        this.audio = audio;
    }

    /**
     * 数据改变了，此方法将调用
     */
    public void dataUpdate() {
        car.update(getPressure(), getFret());
        phone.update(getPressure(), getFret());

        if (audio != null) {
            audio.update(getPressure(), getFret());
        }
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
}
