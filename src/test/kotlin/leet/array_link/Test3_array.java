package leet.array_link;

import com.better.Utils;
import org.junit.Test;

import java.util.Arrays;

public class Test3_array {

    /**
     * 给你一个整数数组 nums 和一个整数 k 。
     * 每一步操作中，你需要从数组中选出和为 k 的 [两个] 整数，并将它们移出数组。
     * 返回你可以对数组执行的最大操作数。
     * 如：nums = [1,2,3,4], k = 5，输出2
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/max-number-of-k-sum-pairs
     */
    @Test
    public void test1() {
//        int nums[] = {3,1,3,4,3};
        int nums[] = {1, 2, 3, 4};
        Utils.println(maxOperations(nums, 5));
    }

    @Test
    public void test2() {
        int nums[] = {1, 2, 3, 4};
        Utils.println(maxOperations2(nums, 5));
    }

    /**
     * 方法2：
     * 使用前后指针
     * @param nums
     * @param k
     * @return
     */
    private int maxOperations2(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        int l = 0;
        int r = nums.length - 1;

        while (l < r) {
            int result = nums[l] + nums[r];
            if (result == k) {
                l++;
                r--;
                count++;
            } else if (result > k) {
                r--;
            } else {
                l++;
            }
        }
        return count;
    }

    private int maxOperations(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                continue;
            }

            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == 0) {
                    continue;
                }
                int result = nums[i] + nums[j];
                if (result == k) {
                    Utils.println(String.format("%s,%s", nums[i], nums[j]));
                    nums[i] = 0;
                    nums[j] = 0;
                    count++;
                    break;
                } else if (result > k) {
                    break;
                }
            }
        }

        return count;
    }
}
