package com.better.pattern.command;

import com.better.pattern.command.light.Light;
import com.better.pattern.command.light.LightOffCommand;
import com.better.pattern.command.light.LightOnCommand;

/**
 * 测试遥控类
 * Created by zhaoyu on 16/11/1.
 */
public class RemoteLoaderWithUndoMain {

	public static void main(String[] args) {
		// 【命令调用者】创建遥控器，
		RemoteControlWithUndo control = new RemoteControlWithUndo();

		// 【命令接收者】创建命令接收者
		Light livingRoomLight = new Light("Living Room");
		Light kitchenLight = new Light("Kitchen Room");

		// 【命令】
		LightOnCommand livingRoomLightOnCommand = new LightOnCommand(livingRoomLight);
		LightOffCommand livingRoomLightOffCommand = new LightOffCommand(livingRoomLight);

		LightOnCommand kitchenLightOnCommand = new LightOnCommand(kitchenLight);
		LightOffCommand kitchenLightOffCommand = new LightOffCommand(kitchenLight);



		// 将命令绑定到遥控上的开关
		control.setCommand(0, livingRoomLightOnCommand, livingRoomLightOffCommand);
		control.setCommand(1, kitchenLightOnCommand, kitchenLightOffCommand);

		System.out.println(control);

		// 开始测试
		control.onBtnPressed(0);
		control.onBtnPressed(1);

		control.undoBtnPressed();
		System.out.println(control.getUndoCmdStr());

		control.offBtnPressed(2);
		control.offBtnPressed(0); //BtnPressed();

		System.out.println(control.getUndoCmdStr());

	}

}
