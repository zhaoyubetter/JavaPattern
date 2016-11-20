package com.better.pattern.observe;

/**
 * Created by zhaoyu on 16/10/9.
 */

public class Main {
	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();
		CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
		StaticConditionsDisplay staticConditionsDisplay = new StaticConditionsDisplay(weatherData);
		weatherData.setMeasurements(22.5f, 100.2f, 33.3f);
	}
}
