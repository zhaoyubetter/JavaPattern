package com.better.sample;


/**
 * String str = "***a**b*d**a**c***";
 * ********abdac
 * Created by zhaoyu1 on 2017/1/19.
 */
public class StringReplace {

    public static void main(String[] args) {
        String str = "***a**b*d**a*c";
//        replace1(str, '*');
//        replace2(str, '*');
        replace3(str, '*');
    }

    /**
     * 从后往前遍历，不断替换
     *
     * @param str
     */
    public static void replace1(String str, char item) {
        final char[] arrays = str.toCharArray();
        int end = -1;   // 最后 item 的索引
        int length = arrays.length;
        for (int i = length - 1; i >= 0; i--) {
            if (arrays[i] == item) {
                if (end < 0) {
                    end = i;
                }
            } else {
                if (end > 0) {
                    arrays[end] = arrays[i];
                    arrays[i] = item;
                    end--;
                }
            }
        }

        System.out.println(new String(arrays));
    }

    /**
     * 不断替换
     * ***bcc**c
     *
     * @param str
     * @param item
     */
    public static void replace2(String str, char item) {
        final StringBuilder sb = new StringBuilder(str);
        final int length = sb.length();
        int itemOffset = 0;
        for (int i = 0; i < length; i++) {
            if (item == sb.charAt(i)) {
                if (itemOffset == i) {
                    itemOffset++;
                } else {
                    sb.deleteCharAt(i);
                    sb.insert(itemOffset++, item);
                }
            }
        }

        System.out.println(sb.toString());
    }

    /**
     * **a*bc**
     * @param str
     * @param item
     */
    static void replace3(String str, char item) {
        int itemOffset = 0;
        final char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(item == chars[i]) {
                if(itemOffset != i) {
                    // 前后转化移动位置
                    chars[i] = (char)(chars[itemOffset] + (chars[itemOffset] = chars[i]) * 0);
                }
                itemOffset++;
            }
        }

        System.out.println(new String(chars));
    }
}
