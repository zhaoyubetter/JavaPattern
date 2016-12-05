package com.better.suanfa;

import com.better.Utils;

/**
 * 选择排序
 * 1.遍历整个数组，将最小的数，放入最前面；
 * 2.遍历余下的数组，找出余下最小的数，并将最小的数，放入最前面
 * 3.重复第二个步骤，直到完成；
 * Created by zhaoyu on 16/3/3.
 */
public class ChooserSort {
	public static void main(String[] args) {
		int a[] = {1, 5, 9, 3, 2, 0, 4, 7, 6, 8};
		//sort1(a);
		sort3(a);
	}

	public static void sort1(int[] a) {
		int outter = 0;
		// 外循环，遍历整个数组
		while (outter < a.length - 1) {
			int min = a[outter];        // 假设最小的数（数组的第一个元素）

			// 内循环
			int inner = outter;
			int minIndex = outter;        // 内循环，查找最小的数的索引
			for (; inner < a.length; inner++) {
				if (min > a[inner]) {
					min = a[inner];
					minIndex = inner;
				}
			}

			if (minIndex != -1) {
				a[minIndex] = a[outter];
			}

			a[outter] = min;
			outter++;
		}

		for (int m = 0; m < a.length; m++) {
			Utils.println(a[m]);
		}
	}

	/**
	 * 进一步优化的版本，减少交换次数
	 *
	 * @param a
	 */
	public static void sort2(int[] a) {
		int outer = 0;
		for (; outer < a.length - 1; outer++) {
			int min = a[outer];        // 最小的数
			int inner = outer;
			int minIndex = outer;    // 最小项索引

			for (; inner < a.length; inner++) {
				if (min > a[inner]) {
					min = a[inner];
					minIndex = inner;
				}
			}

			// 没有找到最小项，重新开始循环
			if (minIndex == outer) {
				continue;
			}

			a[minIndex] = a[outer];
			a[outer] = min;
		}
	}

	/**
	 * 选择排序
	 *
	 * @param a
	 */
	public static void sort3(int a[]) {
		// 算法思路
		// 1.从待排序的数组中，找出最小的元素
		// 2.如果最小元素，不是待排序数组的第一个元素，将其交换
		// 3.从余下的n-1的数组中，找出最小的元素，重复第 1 、2 步，直到排序完成

		int length = a.length;

		// 需要遍历获取最小值的次数；
		// 前面 i 个元素之前的数组，是排序好的，因为后面有 （int j = j+1, 所有这里 length - 1）
		for (int i = 0; i < length - 1; i++) {
			int minValue = a[i];        // 本地循环中找到的最小的数
			int minIndex = i;      // 最小数的索引

			// 遍历待排序数组, 从第 i+1个元素开始往后遍历
			for (int j = i + 1; j < a.length; j++) {
				if (minValue > a[j]) {    // 如果大，则记录新的最小值和下标
					minValue = a[j];
					minIndex = j;
				}
			}

			// 没有找到，重新开始循环，减少交换次数
			if (minIndex == i) {
				continue;
			}

			// 交换2个数
			a[minIndex] = a[i];
			a[i] = minValue;

			System.out.format("第 %d 趟:\t", i + 1);
			Utils.printArray(a);
		}
	}
}
