package com.better.pattern.decorator2.abs;

/**
 * 装饰面包基类
 * Created by zhaoyu on 16/10/23.
 */

public class DecoratorBread implements IBread {

	protected final IBread bread;

	public DecoratorBread(IBread cc) {
		this.bread = cc;
	}

	@Override
	public void prepare() {
		bread.prepare();
	}

	@Override
	public void knead() {
		bread.knead();
	}

	@Override
	public void steadmed() {
		bread.steadmed();
	}

	@Override
	public void process() {
		// 注意，啊，千万别写错了。应该写成下面这3行，而不是直接调用 bread.process();
		// 如果直接写成 bread.process(), 本装饰类的3个方法都不会走了
		//bread.process();
		prepare();
		knead();
		steadmed();
	}
}
