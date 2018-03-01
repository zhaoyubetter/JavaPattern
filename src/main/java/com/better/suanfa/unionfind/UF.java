package com.better.suanfa.unionfind;

/**
 * 并查集：
 * 参考：http://blog.csdn.net/dm_vincent/article/details/7655764
 * Created by zhaoyu on 2016/12/5.
 * <p>
 * quick_find() // 快速查找算法
 */
public class UF {

    private int id[];
    private int count;

    public UF(int size) {
        id = new int[size];
        // 初始化组,节点以整数表示，节点对应的组为 其数组下标
        for (int i = 0; i < size; i++) {
            id[i] = i;
        }
        count = size;
    }

    /**
     * 查找组
     *
     * @param p
     * @return
     */
    public int find(int p) {
        return id[p];
    }

    /**
     * p、q节点是否在同一组
     *
     * @param p
     * @param q
     * @return
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * 连通操作，复杂度偏高 M*N 平方阶
     *
     * @param p
     * @param q
     */
    public void union(int p, int q) {
        // 获取组
        int pID = find(p);
        int qID = find(q);

        // 在同一组，返回
        if (pID == qID) {
            return;
        }

        // 遍历与改变组号
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pID) {
                id[i] = qID;
            }
        }
        count--;    // 组减一
    }

    public int count() {
        return count;
    }
}
