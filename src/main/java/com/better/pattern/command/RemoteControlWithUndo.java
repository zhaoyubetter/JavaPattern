package com.better.pattern.command;

import com.better.pattern.command.abs.ICommand;
import com.better.pattern.command.abs.NoCommand;

/**
 * 遥控器的实现, 加入 undo功能
 * Created by zhaoyu on 16/11/1.
 */
public class RemoteControlWithUndo {
	ICommand[] onCommands;        // 开
	ICommand[] offCommands;       // 关
	ICommand lastCommand;			// 最后的操作命令

	public RemoteControlWithUndo() {
		onCommands = new ICommand[7];
		offCommands = new ICommand[7];

		// 初始化赋值
		ICommand noCmd = new NoCommand();
		for (int i = 0; i < 7; i++) {
			onCommands[i] = noCmd;
			offCommands[i] = noCmd;
		}

		lastCommand = noCmd;
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
		lastCommand = onCommands[slot];
	}

	/**
	 * 关
	 *
	 * @param slot
	 */
	public void offBtnPressed(int slot) {
		offCommands[slot].execute();
		lastCommand = offCommands[slot];
	}

	/**
	 * 撤销按钮点击
	 */
	public void undoBtnPressed() {
		lastCommand.undo();
	}

	public String getUndoCmdStr() {
		return "[undo] " + lastCommand.getClass().getName();
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
