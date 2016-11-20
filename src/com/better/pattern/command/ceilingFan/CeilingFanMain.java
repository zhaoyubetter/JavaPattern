package com.better.pattern.command.ceilingFan;

import com.better.pattern.command.RemoteControlWithUndo;

/**
 * Created by zhaoyu on 16/11/2.
 */

public class CeilingFanMain {
	public static void main(String[] args) {
		// 1.【命令调用者】
		RemoteControlWithUndo controlWithUndo = new RemoteControlWithUndo();
		// 2.【命令接收者】
		CeilingFan ceilingFan = new CeilingFan("Living Room");

		// 3.【命令】
		CeilingFanHighCommand highCommand = new CeilingFanHighCommand(ceilingFan);
		CeilingFanMediumCommand mediumCommand = new CeilingFanMediumCommand(ceilingFan);
		CeilingFanOffCommand offCommand = new CeilingFanOffCommand(ceilingFan);

		// 4.设置遥控
		controlWithUndo.setCommand(0, highCommand, offCommand);
		controlWithUndo.setCommand(1, mediumCommand, offCommand);

		controlWithUndo.onBtnPressed(0);		// 高速开启
		controlWithUndo.offBtnPressed(0);		// 关闭
		System.out.println(controlWithUndo);
		controlWithUndo.undoBtnPressed();		// 撤销，回到高速模式

		controlWithUndo.onBtnPressed(1);		// 中速
		System.out.println(controlWithUndo);
		controlWithUndo.undoBtnPressed();



	}
}
