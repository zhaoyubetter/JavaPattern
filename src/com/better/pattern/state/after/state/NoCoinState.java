package com.better.pattern.state.after.state;

import com.better.pattern.state.after.GumbailMachine;
import com.better.pattern.state.after.abs.State;

/**
 * Created by zhaoyu on 2016/11/18.
 */

public class NoCoinState implements State {

	GumbailMachine machine;

	public NoCoinState(GumbailMachine machine) {
		this.machine = machine;
	}

	@Override
	public void insertCoin() {
		System.out.println("-- 无币状态下，投币请求 --");
		machine.setCurrentState(machine.getHasCoinState());
	}

	@Override
	public void ejectCoin() {
		System.out.println("-- 无币状态下，退币请求  想得美，休想 --");
	}

	@Override
	public void turnCrank() {
		System.out.println("-- 无币状态下，转动  想得美，休想 --");
	}

	// 该状态下不恰当的方法
	@Override
	public void dispense() {
		System.out.println("-- 无币状态下， -- dispense 无糖果分配");
	}

	public String toString() {
		return "未投币";
	}
}
