package com.better.pattern.command.light;

import com.better.pattern.command.abs.ICommand;

/**
 * 开灯
 * Created by zhaoyu on 16/11/1.
 */

public class LightOnCommand implements ICommand {

	Light light;

	public LightOnCommand(Light light) {
		this.light = light;
	}

	@Override
	public void execute() {
		light.on();
	}

	@Override
	public void undo() {
		light.off();
	}
}
