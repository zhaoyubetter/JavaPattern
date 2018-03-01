package com.better.suanfa.unionfind;

/**
 * 并查集：
 * 参考：http://blog.csdn.net/dm_vincent/article/details/7655764
 * Created by zhaoyu on 2016/12/5.
 * <p>
 * 稳定树的层级
 */
public class WeightQuickUF {

    private int id[];
    private int count;
    private int sz[];

    public WeightQuickUF(int size) {
        id = new int[size];
        // 数组的index是节点的整数表示，而相应的值就是该节点的组号了
        for (int i = 0; i < size; i++) {
            id[i] = i;
            sz[i] = 1;      // 默认下，每个组的大小是1
        }
        count = size;
    }

    /**
     * 将节点的父节点指向该节点的爷爷节点，这一点很巧妙，十分方便且有效
     * 相当于在寻找根节点的同时，对路径进行了压缩，使整个树结构扁平化
     *
     * @param p
     * @return
     */
    public int find(int p) {
        while (p != id[p]) {    // 没有达到根
            // 将p节点的父节点设置为它的爷爷节点
            id[p] = id[id[p]];
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
     * 连通操作
     * 节点组织的理想情况应该是一颗十分扁平的树
     *
     * @param p
     * @param q
     */
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);

        // 在同一组，返回
        if (i == j) {
            return;
        }

        // 将小树作为大树的子树
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
        count--;    // 组减一
    }

    public int count() {
        return count;
    }
}
