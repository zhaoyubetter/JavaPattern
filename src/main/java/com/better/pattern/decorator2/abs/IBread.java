package com.better.pattern.decorator2.abs;

/**
 * 面包抽象接口
 * Created by zhaoyu on 16/10/23.
 */

public interface IBread {

	/**
	 * 准备
	 */
	void prepare();

	/**
	 * 揉，和面
	 */
	void knead();

	/**
	 * 蒸
	 */
	void steadmed();

	/**
	 * 加工
	 */
	void process();
}