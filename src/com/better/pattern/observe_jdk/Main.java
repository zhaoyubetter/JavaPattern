package com.better.pattern.observe_jdk;

/**
 * Created by zhaoyu on 16/10/9.
 */
public class Main {
	public static void main(String[] args) {
		WeatherData observable = new WeatherData();
		CurrentConditionsDisplay obser1 = new CurrentConditionsDisplay(observable);
		ForecastDisplay obser2 = new ForecastDisplay(observable);
		observable.setMeasurements(22.2f,33.3f,44.4f);


		// JButton
	}
}
