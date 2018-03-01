package com.better.pattern.observe;

/**
 * 主题 一切从接口开始
 * Created by zhaoyu on 16/10/9.
 */
public interface Subject {
	/**
	 * 注册
	 *
	 * @param o
	 */
	void registerObserver(Observer o);

	/**
	 * 移除
	 *
	 * @param o
	 */
	void removeObserver(Observer o);

	/**
	 * 通知
	 */
	void notifyObservers();
}
