package com.better.pattern.command.stereo;

import com.better.pattern.command.abs.ICommand;

/**
 * Created by zhaoyu on 16/11/1.
 */

public class StereoOffWithDVDCommand implements ICommand {

	Stereo stereo;

	public StereoOffWithDVDCommand(Stereo stereo) {
		this.stereo = stereo;
	}

	@Override
	public void execute() {
		stereo.setVolume(0);
		stereo.off();
	}

	@Override
	public void undo() {
		stereo.setVolume(11);
		stereo.on();
	}
}
