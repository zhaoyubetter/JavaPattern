package com.better.pattern.compound._mvc.controller;

/**
 * Created by zhaoyu on 2017/1/8.
 */
public interface ControllerInterface {
    void start();

    void stop();

    void setBMP(int bmp);

    void increaseBMP();

    void descreaseBMP();
}
