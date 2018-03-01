package com.better.suanfa;

import com.better.Utils;

/**
 * 冒泡排序
 * Created by zhaoyu on 16/3/6.
 */
public class BuddlingSort {
	public static void main(String[] args) {
		int a[] = {1, 5, 9, 3, 2, 0, 4, 7, 6, 8};

		// 冒泡排序
		// 外循环
		for (int outer = 0; outer < a.length; outer++) {
			// 内循环，从后往前
			for (int inner = a.length - 1; inner > outer; inner--) {
				if (a[inner] < a[inner - 1]) {
					int tmp = a[inner];
					a[inner] = a[inner - 1];
					a[inner - 1] = tmp;
				}
			}
		}

		for (int m = 0; m < a.length; m++) {
			Utils.println(a[m]);
		}

		Utils.println("");

		int b[] = {1, 5, 9, 3, 2, 0, 4, 7, 6, 8};
		sort3(b);
	}

	public static void sort(int[] a) {
		// 冒泡排序
		// 外循环
		for (int outer = 0; outer < a.length; outer++) {
			// 内循环，从后往前
			for (int inner = a.length - 1; inner > outer; inner--) {
				if (a[inner] < a[inner - 1]) {
					int tmp = a[inner];
					a[inner] = a[inner - 1];
					a[inner - 1] = tmp;
				}
			}
		}
	}

	public static void sort3(int a[]) {
		Utils.print("排序前:\t\t");
		Utils.printArray(a);

		int len = a.length;
		// 外循环
		for (int out = 0; out < len; out++) {
			// 内循环,从后往前查找,不断冒泡
			for (int inner = len - 1; inner > out; inner--) {
				if (a[inner] < a[inner - 1]) {   // 两两比较,并交换
					int tmp = a[inner];
					a[inner] = a[inner - 1];
					a[inner - 1] = tmp;

					Utils.print(String.format("冒泡数:\t%s\t", tmp));
					Utils.printArray(a);
				}
			}
		}

		Utils.print("排序后: \t\t");
		Utils.printArray(a);
	}
}
