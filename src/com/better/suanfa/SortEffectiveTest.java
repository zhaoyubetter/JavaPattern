package com.better.suanfa;

import com.better.Utils;

import java.util.Random;

/**
 * 排序算法，效率测试
 * Created by zhaoyu on 16/3/27.
 */
public class SortEffectiveTest {
	public static void main(String[] at) {

		final int max = 1000;
		int[] a = new int[max];
		Random rand = new Random(max);
		for (int i = 0; i < max; i++) {
			a[i] = rand.nextInt(max);
		}


		long start = 0;

//		Utils.println("冒泡");
//		start = System.currentTimeMillis();
//		BuddlingSort.sort(a);
//		Utils.println(String.format("冒泡花费：%s 毫秒", (System.currentTimeMillis() - start)));
//
//		Utils.println("选择");
//		start = System.currentTimeMillis();
//		ChooserSort.sort2(a);
//		Utils.println(String.format("选择花费：%s 毫秒", (System.currentTimeMillis() - start)));

//		Utils.println("插入");
//		start = System.currentTimeMillis();
//		InsertionSort.sort2(a);
//		Utils.println(String.format("插入花费：%s 毫秒", (System.currentTimeMillis() - start)));

		Utils.println("归并");
		start = System.currentTimeMillis();
		MergeSortTest.mergeSort(a, 0, max - 1);
		Utils.println(String.format("归并花费：%s 毫秒", (System.currentTimeMillis() - start)));
	}
}