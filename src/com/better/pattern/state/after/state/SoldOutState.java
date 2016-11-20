package com.better.pattern.state.after.state;

import com.better.pattern.state.after.GumbailMachine;
import com.better.pattern.state.after.abs.State;

/**
 * 售罄状态
 * Created by zhaoyu on 2016/11/18.
 */
public class SoldOutState implements State {

	GumbailMachine machine;

	public SoldOutState(GumbailMachine machine) {
		this.machine = machine;
	}

	@Override
	public void insertCoin() {
		System.out.println("-- 售罄状态下，投币请求  无意义 --");
	}

	@Override
	public void ejectCoin() {
		System.out.println("-- 售罄状态下，退币请求  无意义 --");
	}

	@Override
	public void turnCrank() {
		System.out.println("-- 售罄状态下，转动请求  无意义 --");
	}

	@Override
	public void dispense() {
		System.out.println("-- 售罄状态下，dispense  无意义 --");
	}

	public String toString() {
		return "已售罄";
	}
}
