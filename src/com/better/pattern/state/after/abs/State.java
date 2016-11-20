package com.better.pattern.state.after.abs;

/**
 * 状态模式接口
 * Created by zhaoyu on 2016/11/18.
 */
public interface State {
	void insertCoin();

	void ejectCoin();

	void turnCrank();

	void dispense();
}
