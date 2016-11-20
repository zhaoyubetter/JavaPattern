1.模式

继承的缺点：
	1.牵一发，动全身，给其他不想动的子类，造成影响；
	2.运行时的行为不容易改变；
策略模式，将要经常变动的抽取并封装起来，让其他部分不会受影响；

strategy:  来着head first 上的策略模式
strategy2: 打折策略
	8折促销
	满1000-200
	满200,高于200部分8折优惠


command：命令模式
调用者，不需要关心，具体的命令


public abstract class AbsTempletBeverage {
	protected final void prepareRecipe() {
		boilWater();
		brew();
		pourInCup();
		addCondiments();
	}

	protected abstract void addCondiments();

	protected abstract void brew();

	public void boilWater() {
		System.out.println("煮沸水 Boiling water ");
	}


	public void pourInCup() {
		System.out.println("倒入杯中 Pouring into cup");
	}
}
