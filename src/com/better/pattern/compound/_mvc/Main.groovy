package com.better.pattern.compound._mvc

import com.better.pattern.compound._mvc.controller.BeatController
import com.better.pattern.compound._mvc.controller.ControllerInterface
import com.better.pattern.compound._mvc.model.BeatModel
import com.better.pattern.compound._mvc.model.BeatModelInterface

/**
 * Created by zhaoyu on 2017/1/9.
 */
BeatModelInterface model = new BeatModel()
ControllerInterface controller = new BeatController(model)