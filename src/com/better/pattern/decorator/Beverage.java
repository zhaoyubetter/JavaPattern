package com.better.pattern.decorator;

/**
 * 饮料抽象类
 * Created by zhaoyu on 16/10/11.
 */
public abstract class Beverage {

	// 容量设置
	public static final int TALL = 0;		// 小
	public static final int GRANDE = 1;		// 中
	public static final int VENTI = 2;		// 大


	protected String descripiton = "unKnow Beverage";
	protected int size = TALL;

	public String getDescription() {
		return this.descripiton;
	}

	/**
	 * 花费
	 *
	 * @return
	 */
	public abstract double cost();

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
