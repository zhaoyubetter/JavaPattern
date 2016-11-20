package com.better.pattern.state.before;

/**
 * 糖宝贩卖机
 * Created by zhaoyu on 2016/11/17.
 */
public class GumbailMachine {

	// 4个状态：售罄、没投币、投币、售出糖果
	final static int SOLD_OUT = 0;
	final static int NO_COIN = 1;
	final static int HAS_COIN = 2;
	final static int SOLD = 3;

	int state = SOLD_OUT;    // 当前状态
	int count = 0;        // 糖果数量

	public GumbailMachine(int count) {
		this.count = count;
		if (count > 0) {
			state = NO_COIN;
		}
	}

	/**
	 * 投币
	 */
	public void insertCoin() {
		// 各种繁琐的判断
		if (state == HAS_COIN) {
			System.out.println("--投币请求--，不能再投额外的币了");
		} else if (state == NO_COIN) {
			state = HAS_COIN;
			System.out.println("--投币请求--，你投入了一枚硬币");
		} else if (state == SOLD_OUT) {
			System.out.println("--投币请求--，但是糖果已卖完，没有糖果卖了");
		} else if (state == SOLD) {
			System.out.println("--投币请求--，请等下投币，机器正在出货中。。。");
		}
	}

	/**
	 * 要求退回硬币
	 */
	public void ejectCoin() {
		if (state == HAS_COIN) {
			System.out.println("--退币请求--，投币已退");
			state = NO_COIN;
		} else if (state == NO_COIN) {
			System.out.println("--退币请求--，没投过币，不能退币");
		} else if (state == SOLD_OUT) {
			System.out.println("--退币请求--，你都没投过，退个毛币啊");
		} else if (state == SOLD) {
			System.out.println("--退币请求--，系统已在出货中，不能退币了");
		}
	}

	/**
	 * 转动曲柄
	 */
	public void turnCrank() {
		if (state == HAS_COIN) {
			System.out.println("--转动曲柄--, 请等待");
			state = SOLD;
			dispense();
		} else if (state == NO_COIN) {
			System.out.println("--转动曲柄--，但没投过币，不能给你糖果");
		} else if (state == SOLD_OUT) {
			System.out.println("--转动曲柄--，但是糖果已卖完");
		} else if (state == SOLD) {
			System.out.println("--转动曲柄--，正在出货中，继续旋转，也只能出货一次的");
		}
	}

	/**
	 * 发放糖果方法
	 */
	private void dispense() {
		if (state == HAS_COIN) {
			System.out.println("--dispense--，没有糖果发放");
		} else if (state == NO_COIN) {
			System.out.println("--dispense--，没有糖果发放");
		} else if (state == SOLD_OUT) {
			System.out.println("--dispense--，没有糖果发放");
		} else if (state == SOLD) {
			System.out.println("--dispense--，来，请拿你的糖果");
			count--;
			if (count == 0) {
				state = SOLD_OUT;
			} else {
				state = NO_COIN;
			}
		}
	}

	private String getStateDesc() {
		switch (state) {
			case SOLD_OUT:
				return "[售罄]";
			case SOLD:
				return "[销售中]";
			case NO_COIN:
				return "[等待投币中]";
			case HAS_COIN:
				return "[已投币]";
			default:
				return "";
		}
	}

	public String toString() {
		return ">>>> 糖果机：现有糖果 " + count + "颗， 状态是：" + getStateDesc() + "\n";
	}
}
