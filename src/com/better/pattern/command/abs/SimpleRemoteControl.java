package com.better.pattern.command.abs;

/**
 * 操作面板类(一个按钮的遥控)
 * Created by zhaoyu on 16/11/1.
 */
public class SimpleRemoteControl {
	ICommand command;

	public SimpleRemoteControl() {

	}

	/**
	 * 设置具体的命令对象
	 *
	 * @param command
	 */
	public void setCommand(ICommand command) {
		this.command = command;
	}

	public void buttonWasPressed() {
		if (command != null) {
			command.execute();        // 执行具体的命令
		}
	}
}
