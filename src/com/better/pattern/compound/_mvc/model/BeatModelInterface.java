package com.better.pattern.compound._mvc.model;

import com.better.pattern.compound._mvc.observer.BMPObserver;
import com.better.pattern.compound._mvc.observer.BeatObserver;

/**
 * Created by zhaoyu on 2017/1/8.
 */
public interface BeatModelInterface {
    void init();

    void on();

    void off();

    void setBMP(int bmp);

    int getBMP();

    void registerObserver(BeatObserver o);

    void removeObserver(BeatObserver o);

    void registerObserver(BMPObserver o);

    void removeObserver(BMPObserver o);
}
