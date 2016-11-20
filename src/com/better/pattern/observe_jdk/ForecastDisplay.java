package com.better.pattern.observe_jdk;

import com.better.Utils;
import com.better.pattern.observe.DisplayElement;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者2
 * Created by zhaoyu on 16/10/9.
 */
public class ForecastDisplay implements Observer, DisplayElement {

	private Observable observable;

	private float temperature;
	private float humidity;
	private float pressure;

	public ForecastDisplay(Observable o) {
		this.observable = o;
		this.observable.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof WeatherData) {
			WeatherData weatherData = (WeatherData) o;
			temperature = weatherData.getTemperature();
			humidity = weatherData.getHumidity();
			pressure = weatherData.getPressure();

			display();
		}
	}

	@Override
	public void display() {
		Utils.println("ForecastDisplay : " + " 温度: " + this.temperature + " 湿度: " + this.humidity + " 气压: " + this.pressure);
	}
}
