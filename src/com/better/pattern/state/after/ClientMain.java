package com.better.pattern.state.after;

/**
 * Created by zhaoyu on 2016/11/18.
 */

public class ClientMain {
	public static void main(String[] args) {
		GumbailMachine machine = new GumbailMachine(10);

		System.out.println(machine);

		machine.insertCoin();
		machine.turnCrank();

		machine.insertCoin();
		machine.turnCrank();

		machine.insertCoin();
		machine.turnCrank();

		machine.insertCoin();
		machine.turnCrank();

		machine.insertCoin();
		machine.turnCrank();


	}
}
