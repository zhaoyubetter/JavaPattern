package com.better.pattern.command.abs;

/**
 * 命令接口
 * Created by zhaoyu on 16/11/1.
 */
public interface ICommand {
	void execute();

	/**
	 * 撤销命令
	 */
	void undo();
}
