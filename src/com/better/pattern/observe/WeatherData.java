package com.better.pattern.observe;

import java.util.ArrayList;

/**
 * 实现主题接口
 * Created by zhaoyu on 16/10/9.
 */
public class WeatherData implements Subject {

	private ArrayList<Observer> observers;
	private float temperature;
	/**
	 * 湿度
	 */
	private float humidity;
	/**
	 * 压力
	 */
	private float pressure;

	public WeatherData() {
		observers = new ArrayList<>();
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		int i = observers.indexOf(o);
		if (i >= 0) {
			observers.remove(i);
		}
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update(temperature, humidity, pressure);
		}
	}

	/* == 自身类方法 ============================= */
	public void measurementsChanged() {
		notifyObservers();
	}

	public void setMeasurements(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		measurementsChanged();
	}
	/* == 自身类方法 ============================= */
}
