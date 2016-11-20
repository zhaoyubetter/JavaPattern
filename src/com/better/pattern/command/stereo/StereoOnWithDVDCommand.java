package com.better.pattern.command.stereo;

import com.better.pattern.command.abs.ICommand;

/**
 * Created by zhaoyu on 16/11/1.
 */

public class StereoOnWithDVDCommand implements ICommand {

	Stereo stereo;

	public StereoOnWithDVDCommand(Stereo stereo) {
		this.stereo = stereo;
	}

	@Override
	public void execute() {
		stereo.on();
		stereo.setDvd();
		stereo.setVolume(11);
	}

	@Override
	public void undo() {
		stereo.off();
	}
}
