package com.better.pattern.state.before;

/**
 * 测试 糖果机类
 * Created by zhaoyu on 2016/11/17.
 */
public class ClientMain {
	public static void main(String[] args) {
		GumbailMachine machine = new GumbailMachine(5);
		System.out.println(machine);


		machine.insertCoin();		// 投币
		machine.turnCrank();		// 转动

		System.out.println(machine);

		machine.insertCoin();
		machine.ejectCoin();
		machine.turnCrank();

		System.out.println(machine);


		machine.insertCoin();
		machine.turnCrank();
		machine.insertCoin();
		machine.turnCrank();
		machine.ejectCoin();

		System.out.println(machine);

		machine.insertCoin();
		machine.insertCoin();
		machine.turnCrank();
		machine.insertCoin();
		machine.turnCrank();
		machine.insertCoin();
		machine.turnCrank();

		System.out.println(machine);


	}
}
