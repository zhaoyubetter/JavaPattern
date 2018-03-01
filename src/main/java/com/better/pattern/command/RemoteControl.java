package com.better.pattern.command;

import com.better.pattern.command.abs.ICommand;
import com.better.pattern.command.abs.NoCommand;

/**
 * 遥控器的实现
 * Created by zhaoyu on 16/11/1.
 */
public class RemoteControl {
	ICommand[] onCommands;        // 开
	ICommand[] offCommands;       // 关

	public RemoteControl() {
		onCommands = new ICommand[7];
		offCommands = new ICommand[7];

		// 初始化赋值
		ICommand noCmd = new NoCommand();
		for (int i = 0; i < 7; i++) {
			onCommands[i] = noCmd;
			offCommands[i] = noCmd;
		}
	}

	public void setCommand(int slot, ICommand onCommand, ICommand offCommand) {
		onCommands[slot] = onCommand;
		offCommands[slot] = offCommand;
	}

	/**
	 * 开
	 *
	 * @param slot
	 */
	public void onBtnPressed(int slot) {
		onCommands[slot].execute();
	}

	/**
	 * 关
	 *
	 * @param slot
	 */
	public void offBtnPressed(int slot) {
		offCommands[slot].execute();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n ----- Remote Control ----- \n");
		for (int i = 0; i < onCommands.length; i++) {
			sb.append("[slot " + i + "] " + onCommands[i].getClass().getName() + "		" +
					offCommands[i].getClass().getName() + "	\n");
		}

		return sb.toString();
	}
}
