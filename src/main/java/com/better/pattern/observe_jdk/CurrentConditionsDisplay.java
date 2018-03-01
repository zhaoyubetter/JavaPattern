package com.better.pattern.observe_jdk;

import com.better.Utils;
import com.better.pattern.observe.DisplayElement;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者
 * Created by zhaoyu on 16/10/9.
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement {

	Observable observable;
	private float temperature;
	private float humidity;

	/**
	 * 构造函数,还是需要 Observable, 可观察者, 并注册好
	 *
	 * @param observable
	 */
	public CurrentConditionsDisplay(Observable observable) {
		this.observable = observable;
		this.observable.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof WeatherData) {
			WeatherData weatherData = (WeatherData) o;
			this.temperature = weatherData.getTemperature();
			this.humidity = weatherData.getHumidity();
			display();
		}
	}

	@Override
	public void display() {
		Utils.println("CurrentConditionsDisplay : " + " 温度: " + this.temperature + " 湿度: " + this.humidity);
	}
}
