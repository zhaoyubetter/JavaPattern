package com.better.suanfa;

import java.util.Arrays;

/**
 * 归并排序
 * Created by zhaoyu on 16/3/24.
 */
public class MergeSortTest {
	public static void main(String[] args) {
		int a[] = {-10, 20, 0, 3, 90, 5, 2, 1};
		mergeSort(a, 0, a.length - 1);
		System.out.println(Arrays.toString(a));

		System.out.println("===============");
		int b[] = {4, 1};
		merge(b, 0, 0, 1);
		System.out.println(Arrays.toString(b));
	}

	/**
	 * 合并操作
	 * 子数组：a[low, mid], a[mid+1,high] 都是有序的
	 */
	public static void merge(int[] a, int low, int mid, int high) {
		int i = low;            // 第一段序列的下标
		int j = mid + 1;        // 第二段序列的下标
		int k = 0;            // 临时存放合并序列下标

		int[] ta = new int[high - low + 1];        // 临时合并序列
		// 扫描第一段与第二段
		while (i <= mid && j <= high) {
			if (a[i] <= a[j]) {
				ta[k] = a[i];
				i++;
			} else {
				ta[k] = a[j];
				j++;
			}
			k++;
		}

		// 收尾操作
		while (i <= mid) {
			ta[k] = a[i];
			i++;
			k++;
		}
		while (j <= high) {
			ta[k] = a[j];
			j++;
			k++;
		}

		// 将合并序列 复制到原始数组中
		for (k = 0, i = low; i <= high; i++, k++) {
			a[i] = ta[k];
		}
	}

	public static void mergeSort(int[] a, int low, int high) {
		int mid = (low + high) / 2;
		if (low < high) {
			// 左边
			mergeSort(a, low, mid);
			// 右边
			mergeSort(a, mid + 1, high);
			// 合并
			merge(a, low, mid, high);
		}
	}
}
