package com.better.pattern.command.light;

/**
 * 具体灯，命令接收者对象
 * Created by zhaoyu on 16/11/1.
 */
public class Light {

	String name;

	public Light(String name) {
		this.name = name;
	}

	public void on() {
		System.out.println(name + " 开灯...");
	}

	public void off() {
		System.out.println(name + " 关灯...");
	}
}
