package com.better.suanfa;

import com.better.Utils;

/**
 * 快速排序
 * Created by zhaoyu on 16/9/28.
 */

public class QuickSort {
	public static void main(String[] args) {
		//int list[] = {6, 5, 2, 1, 7, 4, 5, 8, 3};
		//int list[] = {2,5,3};

		int[] list = {1, 3, 4, 5, 2, 6, 9, 7, 8, 0};

		Utils.print("排序前\t\t");
		Utils.printArray(list);

		QuickSort q = new QuickSort();
		//q.quickSort(list, 0, list.length - 1);
		q.quickSort2(list, 0, list.length - 1);

		Utils.print("排序后\t\t");
		Utils.printArray(list);
	}


	private void quickSort2(int a[], int left, int right) {
		// 前置检查
		if (left > right) {
			return;
		}


		// 记录,参数值
		int i, j, baseValue, t;
		baseValue = a[left];
		i = left;
		j = right;

		while (i < j) {
			// 从右往左,查找大于base的数 (下标 j 对应的数)
			while (a[j] >= baseValue && i < j) {
				j--;
			}

			// 从左往右,查找小于base的数 (下标 i 对应的数)
			while (a[i] <= baseValue && i < j) {
				i++;
			}

			// 【交换部分】交换2个数
			if (i < j) {
				t = a[i];
				a[i] = a[j];
				a[j] = t;
			}
		}

		// 【交换部分】基数归位
		a[left] = a[i];
		a[i] = baseValue;

		System.out.format("base = %d:\t", a[i]);
		printPart(a, left, right);

		// 分治,减小区间
		quickSort2(a, left, i - 1);        // 处理左边的
		quickSort2(a, i + 1, right);        // 处理右边的

		//Utils.println(String.format("i=%s, j=%s, base=%s, left=%s, right=%s", i, j, base, a[i], a[j]));
	}


	// 打印序列
	public void printPart(int[] list, int begin, int end) {
		for (int i = 0; i < begin; i++) {
			System.out.print("\t");
		}
		for (int i = begin; i <= end; i++) {
			System.out.print(list[i] + "\t");
		}
		System.out.println();
	}


	/* ============= sort */

	/**
	 * 快速排序
	 *
	 * @param a
	 * @param left
	 * @param right
	 */
	private void quickSort(int a[], int left, int right) {
		if (left > right) {
			return;
		}

		int i = left;
		int j = right;
		int baseValue = a[left];        // 对比基数
		int temp;

		while (i < j) {
			// 从右往左走,查找比baseValue大的数
			while (a[j] >= baseValue && i < j) {
				j--;
			}

			// 从左往右走,查找比baseValue小的数
			while (a[i] <= baseValue && i < j) {
				i++;
			}

			// 交换2个 找到的数
			if (i < j) {
				temp = a[i];
				a[i] = a[j];
				a[j] = temp;
			}
		}

		// 每次查找完毕时,交换 left 与 baseValue的值, 这时, baseValue 左边比它都小,
		// 右边比它都大
		a[left] = a[i];
		a[i] = baseValue;

		// 分治,分别快速排序
		quickSort(a, left, i-1);
		quickSort(a, i+1, right);
	}


}
