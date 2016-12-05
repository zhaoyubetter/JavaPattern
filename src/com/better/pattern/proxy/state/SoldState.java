package com.better.pattern.proxy.state;

import com.better.pattern.proxy.GumbailMachine;
import com.better.pattern.proxy.abs.State;

/**
 * 销售状态
 * Created by zhaoyu on 2016/11/18.
 */
public class SoldState implements State {

	transient GumbailMachine machine;

	public SoldState(GumbailMachine machine) {
		this.machine = machine;
	}

	@Override
	public void insertCoin() {
		System.out.println("-- 销售状态下，投币请求		无意义 --");
	}

	@Override
	public void ejectCoin() {
		System.out.println("-- 销售状态下，退币请求		无意义 --");
	}

	@Override
	public void turnCrank() {
		System.out.println("-- 销售状态下，转动请求		无意义 --");
	}

	/**
	 * 销售状态下，最主要的方法
	 */
	@Override
	public void dispense() {
		machine.releaseBall();
		if (machine.getCount() > 0) {
			machine.setCurrentState(machine.getNoCoinState());
		} else {
			System.out.println("  》》》》糖果售罄");
			machine.setCurrentState(machine.getSoldOutState());
		}
	}

	public String toString() {
		return "销售中";
	}
}
