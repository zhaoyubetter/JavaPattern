package com.better.pattern.compound._mvc.controller;

import com.better.pattern.compound._mvc.model.BeatModelInterface;
import com.better.pattern.compound._mvc.view.DJView;

/**
 * 控制器
 * Created by zhaoyu on 2017/1/9.
 */
public class BeatController implements ControllerInterface {

    BeatModelInterface model;
    DJView view;

    public BeatController(BeatModelInterface model) {
        this.model = model;
        view = new DJView(this, model);
        view.createView();
        view.createControls();
        view.disableStopMenuItem();
        view.enableStartMenuItem();
        model.init();
    }

    @Override
    public void start() {
        model.on();
        view.disableStartMenuItem();;
        view.enableStopMenuItem();
    }

    @Override
    public void stop() {
        model.off();
        view.disableStopMenuItem();
        view.enableStartMenuItem();
    }

    @Override
    public void setBMP(int bmp) {
        model.setBMP(bmp);
    }

    @Override
    public void increaseBMP() {
        model.setBMP(model.getBMP() + 1);
    }

    @Override
    public void descreaseBMP() {
        model.setBMP(model.getBMP() - 1);
    }
}
