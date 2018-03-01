package com.better.pattern.command.light;

import com.better.pattern.command.abs.ICommand;

/**
 * 关灯命令
 * Created by zhaoyu on 16/11/1.
 */
public class LightOffCommand implements ICommand {

	Light light;

	public LightOffCommand(Light light) {
		this.light = light;
	}

	@Override
	public void execute() {
		light.off();
	}

	@Override
	public void undo() {
		light.on();
	}
}
