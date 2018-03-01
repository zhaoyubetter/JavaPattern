package com.better.pattern.adapter.abs;

/**
 * 适配器
 * Created by zhaoyu on 2016/11/5.
 */
public class TurkeyAdapter implements Duck {

	/**
	 * 实际对象
	 */
	Turkey turkey;

	public TurkeyAdapter(Turkey turkey) {
		this.turkey = turkey;
	}

	@Override
	public void quack() {
		this.turkey.gobble();
	}

	@Override
	public void fly() {
		for (int i = 0; i < 5; i++) {
			this.turkey.fly();
		}
	}
}
