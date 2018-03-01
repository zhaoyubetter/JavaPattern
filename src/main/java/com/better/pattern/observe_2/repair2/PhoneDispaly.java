package com.better.pattern.observe_2.repair2;


import com.better.pattern.observe_2.after.Wheel;

/**
 * Created by zhaoyu on 2016/12/14.
 */
public class PhoneDispaly extends Observer {

    private Subject subject;

    public PhoneDispaly(Subject subject) {
        this.subject = subject;
        this.subject.addObserver(this);
    }

    @Override
    public void update(Subject subject) {
        if(subject instanceof Wheel) {
            Wheel a = (Wheel) subject;
            a.getPressure();
            a.getFret();
            a.getHeight();
        }
    }
}
