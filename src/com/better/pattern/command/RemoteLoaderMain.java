package com.better.pattern.command;

import com.better.pattern.command.light.Light;
import com.better.pattern.command.light.LightOffCommand;
import com.better.pattern.command.light.LightOnCommand;
import com.better.pattern.command.stereo.Stereo;
import com.better.pattern.command.stereo.StereoOffWithDVDCommand;
import com.better.pattern.command.stereo.StereoOnWithDVDCommand;

/**
 * 测试遥控类
 * Created by zhaoyu on 16/11/1.
 */
public class RemoteLoaderMain {

	public static void main(String[] args) {
		// 【命令调用者】创建遥控器，
		RemoteControl control = new RemoteControl();

		// 【命令接收者】创建命令接收者
		Light livingRoomLight = new Light("Living Room");
		Light kitchenLight = new Light("Kitchen Room");
		Stereo dinningStereo = new Stereo("Dinning Room");
		Stereo hollStereo = new Stereo("Holl Room");

		// 【命令】
		LightOnCommand livingRoomLightOnCommand = new LightOnCommand(livingRoomLight);
		LightOffCommand livingRoomLightOffCommand = new LightOffCommand(livingRoomLight);

		LightOnCommand kitchenLightOnCommand = new LightOnCommand(kitchenLight);
		LightOffCommand kitchenLightOffCommand = new LightOffCommand(kitchenLight);


		StereoOnWithDVDCommand dinningStereoOnCommand = new StereoOnWithDVDCommand(dinningStereo);
		StereoOffWithDVDCommand dinningStereoOffCommand = new StereoOffWithDVDCommand(dinningStereo);

		StereoOnWithDVDCommand hollStereoOnCommand = new StereoOnWithDVDCommand(hollStereo);
		StereoOffWithDVDCommand hollStereoOffCommand = new StereoOffWithDVDCommand(hollStereo);


		// 将命令绑定到遥控上的开关
		control.setCommand(0, livingRoomLightOnCommand, livingRoomLightOffCommand);
		control.setCommand(1, kitchenLightOnCommand, kitchenLightOffCommand);
		control.setCommand(2, dinningStereoOnCommand, dinningStereoOffCommand);
		control.setCommand(3, hollStereoOnCommand, hollStereoOffCommand);

		System.out.println(control);


		// 开始测试
		control.onBtnPressed(0);
		control.onBtnPressed(1);
		control.offBtnPressed(2);
		control.offBtnPressed(0); //BtnPressed();

	}

}
