package com.better.pattern.command.ceilingFan;

import com.better.pattern.command.abs.ICommand;

/**
 * 风扇调速命令 (高速命令)
 * Created by zhaoyu on 16/11/2.
 */
public class CeilingFanHighCommand implements ICommand {

	CeilingFan ceilingFan;
	int prevSpeed;

	public CeilingFanHighCommand(CeilingFan ceilingFan) {
		this.ceilingFan = ceilingFan;
	}

	@Override
	public void execute() {
		prevSpeed = ceilingFan.getSpeed();        // 上一个速度
		ceilingFan.high();
	}

	@Override
	public void undo() {
		// 速度设置回之前的值，达到撤销的目的
		switch (prevSpeed) {
			case CeilingFan.HIGH:
				ceilingFan.high();
				break;
			case CeilingFan.MEDIUM:
				ceilingFan.medium();
				break;
			case CeilingFan.LOW:
				ceilingFan.low();
				break;
			case CeilingFan.OFF:
				ceilingFan.off();
				break;
		}
	}
}
