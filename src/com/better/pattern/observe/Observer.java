package com.better.pattern.observe;

/**
 * 观察者
 * Created by zhaoyu on 16/10/9.
 */
public interface Observer {
	/**
	 * 改变时,调用此方法
	 *
	 * @param temp
	 * @param humidity
	 * @param pressure
	 */
	void update(float temp, float humidity, float pressure);
}
