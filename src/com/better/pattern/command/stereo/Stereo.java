package com.better.pattern.command.stereo;

/**
 * 音响
 * Created by zhaoyu on 16/11/1.
 */
public class Stereo {

	private int volume;

	String name;

	public Stereo(String name) {
		this.name = name;
	}

	public void on() {
		System.out.println(name + " Stereo on open...");
	}

	public void off() {
		System.out.println(name + " Stereo shutdown...");
	}

	public void setCd() {
		System.out.println(name + " Stereo Play CD");
	}

	public void setDvd() {
		System.out.println(name + " Stereo Play DVD");
	}

	public void setVolume(int volume) {
		this.volume = volume;
		System.out.println(name + " Stereo set Volume [" + volume +"]" );
	}
}
