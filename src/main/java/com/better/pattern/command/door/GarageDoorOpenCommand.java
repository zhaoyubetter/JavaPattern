package com.better.pattern.command.door;

import com.better.pattern.command.abs.ICommand;

/**
 * GarageDoorOpen 命令
 * Created by zhaoyu on 16/11/1.
 */
public class GarageDoorOpenCommand implements ICommand {

	GarageDoorOpen garageDoorOpen;

	public GarageDoorOpenCommand(GarageDoorOpen garageDoorOpen) {
		this.garageDoorOpen = garageDoorOpen;
	}

	@Override
	public void execute() {
		this.garageDoorOpen.up();
	}

	@Override
	public void undo() {
		this.garageDoorOpen.down();
	}
}
