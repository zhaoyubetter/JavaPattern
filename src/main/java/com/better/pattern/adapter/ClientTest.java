package com.better.pattern.adapter;

import com.better.pattern.adapter.abs.Duck;
import com.better.pattern.adapter.abs.TurkeyAdapter;

/**
 * Created by zhaoyu on 2016/11/5.
 */

public class ClientTest {
	public static void main(String[] args) {

		MallardDuck duck = new MallardDuck();

		WildTurkey turkey = new WildTurkey();
		Duck turkeyAdapter = new TurkeyAdapter(turkey);

		System.out.println("---------- The Turkey Start ---------- ");
		turkey.gobble();
		turkey.fly();
		System.out.println("---------- The Turkey End ---------- ");


		System.out.println("---------- The Duck Start ----------");
		testDuck(duck);
		System.out.println("---------- The Duck End ----------");


		System.out.println("---------- The TurkeyAdapter Start ----------");
		testDuck(turkeyAdapter);
		System.out.println("---------- The TurkeyAdapter End ----------");


	}

	private static void testDuck(Duck duck) {
		duck.quack();
		duck.fly();
	}
}
