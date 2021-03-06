package com.better.pattern.state.after;

import com.better.pattern.state.after.abs.State;
import com.better.pattern.state.after.state.HasCoinState;
import com.better.pattern.state.after.state.NoCoinState;
import com.better.pattern.state.after.state.SoldOutState;
import com.better.pattern.state.after.state.SoldState;
import com.better.pattern.state.after.state.WinState;

/**
 * 糖宝贩卖机
 * Created by zhaoyu on 2016/11/17.
 */
public class GumbailMachine {

	// 4个状态：分别用不同的状态类表示 售罄、没投币、投币、售出糖果
	final State soldOutState;
	final State noCoinState;
	final State hasCoinState;
	final State soldState;
	final State winState;    // 赢家状态

	int count = 0;        // 糖果数量
	State currentState;      // 当前状态

	public GumbailMachine(int count) {
		this.count = count;
		soldOutState = new SoldOutState(this);
		noCoinState = new NoCoinState(this);
		hasCoinState = new HasCoinState(this);
		soldState = new SoldState(this);
		winState = new WinState(this);

		if (count > 0) {
			currentState = noCoinState;
		}
	}

	/**
	 * 投币
	 */
	public void insertCoin() {
		currentState.insertCoin();
	}

	/**
	 * 要求退回硬币
	 */
	public void ejectCoin() {
		currentState.ejectCoin();
	}

	/**
	 * 转动曲柄
	 */
	public void turnCrank() {
		currentState.turnCrank();
		currentState.dispense();
	}

	/**
	 * 释放一个糖果
	 */
	public void releaseBall() {
		System.out.println("----> 释放一个糖果，请拿着你的糖果");
		if (count != 0) {
			count--;
		}
	}


	public void setCurrentState(State state) {
		this.currentState = state;
	}

	public State getSoldOutState() {
		return soldOutState;
	}

	public State getNoCoinState() {
		return noCoinState;
	}

	public State getHasCoinState() {
		return hasCoinState;
	}

	public State getSoldState() {
		return soldState;
	}

	public State getWinState() {
		return winState;
	}

	public int getCount() {
		return count;
	}


	public String toString() {
		return ">>>> 糖果机： 状态:" + currentState + " 现有糖果 " + count + "颗\n";
	}
}
