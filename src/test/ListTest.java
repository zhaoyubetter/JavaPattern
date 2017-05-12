package test;

import com.better.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Arrays;

/**
 * Created by zhaoyu on 2016/12/16.
 */
public class ListTest {
    public static void main(String[] arg) {
        ArrayList list = new ArrayList();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);

        int[] a = new int[10];
        for (int i = 0; i < list.size(); i++) {
            a[i] = (int) list.get(i);
        }

        // 从原数组中，从下标0开始，拉出5个元素形成新数组
        int[] ints = Arrays.copyOf(a, 5);


        Utils.printArray(ints);

    }
}
