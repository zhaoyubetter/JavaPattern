package com.better.suanfa.unionfind;

/**
 * 并查集：
 * 参考：http://blog.csdn.net/dm_vincent/article/details/7655764
 * Created by zhaoyu on 2016/12/5.
 * <p>
 * 快速合并
 */
public class QuickUF {

    private int id[];
    private int count;

    public QuickUF(int size) {
        id = new int[size];
        // 数组的index是节点的整数表示，而相应的值就是该节点的组号了
        for (int i = 0; i < size; i++) {
            id[i] = i;
        }
        count = size;
    }

    /**
     * 查找组的根节点
     *
     * @param p
     * @return
     */
    public int find(int p) {
        // 寻找p节点所在组的根节点，根节点具有性质id[tree] = tree
        while (p != id[p]) {
            p = id[p];
        }
        return p;
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
     * 连通操作，简化
     *
     * @param p
     * @param q
     */
    public void union(int p, int q) {
        // 分别获取节点的根组
        int pRoot = find(p);
        int qRoot = find(q);

        // 在同一组，返回
        if (pRoot == qRoot) {
            return;
        }

        // 将一颗树(即一个组)变成另外一课树(即一个组)的子树
        id[pRoot] = qRoot;
        count--;    // 组减一
    }

    public int count() {
        return count;
    }
}
