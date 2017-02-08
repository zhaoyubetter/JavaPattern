package com.better.sample;

import java.util.Arrays;

/**
 * 2分查找二维数组中某个数
 * Created by zhaoyu1 on 2016/10/31.
 */
public class MyClass {
    public static void main(String[] args) {
        // 2维数组：3行，6列数组
        int[][] array = {
                {1, 2, 3, 4, 5, 6},
                {7, 8, 11, 12},
                {13, 14, 15, 16, 17, 18}
        };

        System.out.println(find2(array, 11));
    }

    public static boolean find2(int[][] array, int target) {
        int len = array.length - 1;
        int i = 0;
        while ((len >= 0) && (i < array[0].length)) {
            if (array[len][i] > target) {
                len--;
            } else if (array[len][i] < target) {
                i++;
            } else {
                return true;
            }
        }
        return false;

    }

    public static boolean Find(int[][] array, int target) {
        // 1.判断每列的最后一个元素是否 大于等于 target
        boolean find = false;
        // 遍历每一行，获取最后一个元素
        for (int row = 0; row < array.length; row++) {
            int col = array[row].length - 1;    // 每一行对应的最后一列
            if (col <= 0) {
                continue;
            }
            int last = array[row][col]; // 最后一列的值
            if (last < target) {        // 整行的数据都小，进行下一行比较
                continue;
            } else {
                // 查找整行
                int index = Arrays.binarySearch(array[row], target);
                if (index < 0) {
                    continue;       // 下一行
                } else {
                    find = true;
                    System.out.println("found it[" + row + "," + index + "]");
                    break;
                }
            }
        }
        return find;
    }
}
