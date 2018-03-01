package com.better.pattern.command;

import com.better.pattern.command.abs.SimpleRemoteControl;
import com.better.pattern.command.door.GarageDoorOpen;
import com.better.pattern.command.door.GarageDoorOpenCommand;
import com.better.pattern.command.light.Light;
import com.better.pattern.command.light.LightCommand;

/**
 * Created by zhaoyu on 16/11/1.
 */

public class ClientMain {

	public static void main(String[] args) {
		// 【调用者】	创建简单遥控器，可用来发出请求（模拟按下按钮）
		SimpleRemoteControl control = new SimpleRemoteControl();
		// 【接收者】	创建一个电灯对象(请求的接收者)
		Light light = new Light("");
		// 【命令】	创建命令，并将，接收者，传入
		LightCommand lightCommand = new LightCommand(light);

		// 命令传给调用者
		control.setCommand(lightCommand);
		control.buttonWasPressed();	// 调用者调用（模拟按钮按下）


		// ================ GarageDoorOpen
		// 1.创建接收者
		GarageDoorOpen garageDoorOpen = new GarageDoorOpen();
		// 2.创建命令,命令与接收者对应
		GarageDoorOpenCommand garageDoorOpenCommand = new GarageDoorOpenCommand(garageDoorOpen);
		// 3.传入命令
		control.setCommand(garageDoorOpenCommand);
		// 4.执行命令
		control.buttonWasPressed();
	}
}
