package com.better.pattern.command.party;

import com.better.pattern.command.abs.ICommand;

/**
 * 一次执行多个命令
 * Created by zhaoyu on 16/11/2.
 */
public class BetterCommand implements ICommand {

	ICommand[] commands = null;

	public BetterCommand(ICommand[] commands) {
		this.commands = commands;
	}

	@Override
	public void execute() {
		// 执行多个命令，主命令下的多个命令
		for (ICommand cmd : commands) {
			cmd.execute();
		}
	}

	@Override
	public void undo() {
		for (ICommand cmd : commands) {
			cmd.undo();
		}
	}
}
