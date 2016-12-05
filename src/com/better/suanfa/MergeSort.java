package com.better.suanfa;

import com.better.Utils;

import java.util.Arrays;

/**
 * 归并配置，与
 *
 * @see MergeSortTest 一致
 * Created by zhaoyu on 16/3/27.
 */
public class MergeSort {
	public static void main(String[] args) {
		int a[] = {-10, 20, 0, 3, 90, 5, 2, 1};
		mergeSort(a, 0, a.length - 1);
	}

	/**
	 * 归并排序——合并操作
	 *
	 * @param a    数组
	 * @param low  下标
	 * @param mid  中间下标
	 * @param high
	 */
	private static final void merge(int[] a, int low, int mid, int high) {
		int i = low;            // 第一段序列开始下标
		int j = mid + 1;        // 第二段序列开始下标
		int k = 0;                // 临时数组下标

		// 创建临时数组
		int[] tempA = new int[high - low + 1];
		while (i <= mid && j <= high) {
			if (a[i] < a[j]) {
				tempA[k] = a[i];
				i++;
			} else {
				tempA[k] = a[j];
				j++;
			}
			k++;
		}

		// 收尾工作
		while (i <= mid) {
			tempA[k] = a[i];
			i++;
			k++;
		}

		while (j <= high) {
			tempA[k] = a[j];
			j++;
			k++;
		}

		// copy 到原数组
		for (i = low, k = 0; i <= high; i++, k++) {
			a[i] = tempA[k];
		}
	}

	/**
	 * 归并排序
	 *
	 * @param a
	 * @param low  最低位
	 * @param high 最高位
	 */
	private static final void mergeSort(int[] a, int low, int high) {
		int mid = (low + high) / 2;
		// 如果 >= ，证明只有一个元素，所以已经排序好了，所以这里是 <
		if (low < high) {
			// 左半部分
			mergeSort(a, low, mid);
			// 右半部分
			mergeSort(a, mid + 1, high);
			// 合并操作
			merge(a, low, mid, high);
			Utils.println(Arrays.toString(a));
		}
	}
}
