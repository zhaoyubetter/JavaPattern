package com.better.suanfa.min_sub_sequence;

import java.util.Random;

/**
 * Created by zhaoyu on 2016/11/13.
 */

public final class DataFactory {
	public static int[] generateData(int size) {
		int[] a = new int[size];
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			int number = random.nextInt(100);
			a[i] = number * ((number % 2 == 0) ? 1 : -1);
		}
		return a;
	}
}
