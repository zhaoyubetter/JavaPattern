package com.better.pattern.observe_jdk;

import java.util.Observable;

/**
 * 可观察者
 * Created by zhaoyu on 16/10/9.
 */
public class WeatherData extends Observable {
	private float temperature;
	private float humidity;
	private float pressure;


	public void measurementsChanged() {
		setChanged();
		notifyObservers();
	}

	public void setMeasurements(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		measurementsChanged();
	}


	// == for 观察者 拉取的方法 ================

	public float getTemperature() {
		return temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public float getPressure() {
		return pressure;
	}
}
