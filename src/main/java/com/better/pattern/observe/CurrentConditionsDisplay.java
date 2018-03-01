package com.better.pattern.observe;

import com.better.Utils;

/**
 * 观察者,订阅对象
 * Created by zhaoyu on 16/10/9.
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement {

	private float temperature;
	private float humidity;
	private Subject weacherData;        // 主题对象

	/**
	 * 创建 观察者对象, 并通过 subject 注册 该 观察者对象
	 *
	 * @param subject
	 */
	public CurrentConditionsDisplay(Subject subject) {
		this.weacherData = subject;
		subject.registerObserver(this);
	}

	@Override
	public void update(float temp, float humidity, float pressure) {
		this.temperature = temp;
		this.humidity = humidity;
		// 数据改变时,调用display来展示数据
		display();
	}

	@Override
	public void display() {
		Utils.println("Current conditions: " + temperature + ", 湿度:" + humidity);
	}
}
