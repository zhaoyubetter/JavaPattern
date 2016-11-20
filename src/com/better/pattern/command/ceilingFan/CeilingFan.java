package com.better.pattern.command.ceilingFan;

/**
 * 风扇多档位
 * Created by zhaoyu on 16/11/2.
 */
public class CeilingFan {
	public static final int HIGH = 3;
	public static final int MEDIUM = 2;
	public static final int LOW = 1;
	public static final int OFF = 0;

	String location;        // 位置
	int speed;                // 速度

	public CeilingFan(String location) {
		this.location = location;
		this.speed = OFF;
	}

	public void high() {
		this.speed = HIGH;
		System.out.println(location + " ceiling fan is on high");
	}

	public void medium() {
		this.speed = MEDIUM;
		System.out.println(location + " ceiling fan is on medium");
	}

	public void low() {
		this.speed = LOW;
		System.out.println(location + " ceiling fan is on low");
	}

	public void off() {
		this.speed = OFF;
		System.out.println(location + " ceiling fan is off");
	}

	public int getSpeed() {
		return this.speed;
	}
}
