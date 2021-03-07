package leet.binary;

import com.better.Utils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class Test1_TwoSum {
    // 在数组中找到 2 个数之和等于给定值的数字，结果返回 2 个数字在数组中的下标。
    /*
        Given nums = [2, 7, 11, 15], target = 9,
        Because nums[0] + nums[1] = 2 + 7 = 9,
        return [0, 1]
     */
    @Test
    public void test1() {
        int arr[] = {2, 7, 11, 15};
        int target = 9;
        //(value, index)
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(target - arr[i])) {
                Utils.println(String.format("%s, %s", map.get(target - arr[i]), i));
                break;
            }
            map.put(arr[i], i);
        }
    }
}
