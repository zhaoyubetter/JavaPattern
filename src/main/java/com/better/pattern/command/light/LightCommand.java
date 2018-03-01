package com.better.pattern.command.light;

import com.better.pattern.command.abs.ICommand;

/**
 * 开灯 命令对象，持有灯接收者
 * Created by zhaoyu on 16/11/1.
 */

public class LightCommand implements ICommand {

	Light light;

	public LightCommand(Light light) {
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
