package com.better.suanfa.min_sub_sequence;

import static com.better.suanfa.min_sub_sequence.DataFactory.generateData;

/**
 * 最大子序列之和 //最小不会小过0。
 * Created by zhaoyu on 2016/11/13.
 */

public class SubSequence {

	public static void main(String[] args) {
		int[] data = generateData(9999);

		long startTime = System.currentTimeMillis();
		int i = test1(data);
		System.out.println(i);
		System.out.println("方法一，花费时间：" + (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		int j = test2(data);
		System.out.println(j);
		System.out.println("方法二，花费时间：" + (System.currentTimeMillis() - startTime));


	}

	/**
	 * 三层循环的算法
	 *
	 * @param data
	 */
	private static int test1(int[] data) {
		int maxSum = 0;
		int curSum;
		for (int i = 0; i < data.length; i++) {
			for (int j = i; j < data.length; j++) {
				curSum = 0;
				for (int k = i; k <= j; k++) {        // 区间相加
					curSum += data[k];
				}
				maxSum = Math.max(maxSum, curSum);
			}
		}

		return maxSum;
	}

	/**
	 * 二层循环，去掉多余的相加
	 *
	 * @param data
	 * @return
	 */
	private static int test2(int[] data) {
		int maxSum = 0;
		int curSum;
		for (int i = 0; i < data.length; i++) {
			curSum = 0;        // 复位
			for (int j = i; j < data.length; j++) {
				curSum += data[j];
				maxSum = Math.max(maxSum, curSum);
			}
		}

		return maxSum;
	}

}
