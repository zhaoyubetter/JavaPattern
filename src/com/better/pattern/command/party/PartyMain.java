package com.better.pattern.command.party;

import com.better.pattern.command.RemoteControlWithUndo;
import com.better.pattern.command.abs.ICommand;
import com.better.pattern.command.light.Light;
import com.better.pattern.command.light.LightOffCommand;
import com.better.pattern.command.light.LightOnCommand;
import com.better.pattern.command.stereo.Stereo;
import com.better.pattern.command.stereo.StereoOffWithDVDCommand;
import com.better.pattern.command.stereo.StereoOnWithDVDCommand;

/**
 * Created by zhaoyu on 16/11/2.
 */
public class PartyMain {

	public static void main(String[] args) {
		// [命令接收者]
		Light light = new Light("Living Room");
		Stereo stereo = new Stereo("Living Room");

		// [命令]
		LightOnCommand lightOnCommand = new LightOnCommand(light);
		StereoOnWithDVDCommand stereoOnWithDVDCommand = new StereoOnWithDVDCommand(stereo);

		LightOffCommand lightOffCommand = new LightOffCommand(light);
		StereoOffWithDVDCommand stereoOffWithDVDCommand = new StereoOffWithDVDCommand(stereo);

		// [命令集合]
		ICommand[] partyOn = {lightOnCommand, stereoOnWithDVDCommand};
		ICommand[] partyOff = {lightOffCommand, stereoOffWithDVDCommand};

		// ** 主命令 **
		BetterCommand betterOnCommand = new BetterCommand(partyOn);
		BetterCommand betterOffCommand = new BetterCommand(partyOff);

		// [命令调用者]
		RemoteControlWithUndo remoteControlWithUndo = new RemoteControlWithUndo();
		remoteControlWithUndo.setCommand(0, betterOnCommand, betterOffCommand);

		// 测试
		System.out.println(remoteControlWithUndo);
		System.out.println("---- Press On Button ----");
		remoteControlWithUndo.onBtnPressed(0);
		System.out.println("---- Press Off Button ----");
		remoteControlWithUndo.offBtnPressed(0);



	}
}
