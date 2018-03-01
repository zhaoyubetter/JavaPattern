package com.better.pattern.strategy2;

import java.util.Random;

/**
 * Created by zhaoyu on 16/9/20.
 */

public class Test {



	/**
	 * 模拟策略模式,客户端测试
	 */
	@org.junit.Test
	public void testClient() {

		Context ctx = new Context();
		Random r = new Random();

		for (int i = 0; i < 10; i++) {
			int strate = r.nextInt(3);        // 打折策略
			double consumePrice = 0;
			double realPayPrice = 0;
			String priceStrategy = "";

			while ((consumePrice = r.nextInt(2000)) == 0) {

			}


			switch (strate) {
				case 0:
					ctx.setStrategy(new RebateStrategy());
					realPayPrice = ctx.getRealPrice(consumePrice);
					priceStrategy = "打八折";
					break;
				case 1:
					ctx.setStrategy(new OverRebateStrategy());
					realPayPrice = ctx.getRealPrice(consumePrice);
					priceStrategy = "超出200部分,打八折";
					break;
				case 2:
					ctx.setStrategy(new OverCutDownStrategy());
					realPayPrice = ctx.getRealPrice(consumePrice);
					priceStrategy = "满1000减200";
					break;
			}

			System.out.print(String.format("【%s】", priceStrategy));
			System.out.println(String.format("原价: [%.2f], 打折后价格: [%.2f]", consumePrice, realPayPrice));
		}


	}
}
