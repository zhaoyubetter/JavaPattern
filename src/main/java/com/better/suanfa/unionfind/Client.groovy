package com.better.suanfa.unionfind

/**
 * Created by zhaoyu on 2016/12/5.
 */
class Client {
    static void main(args) {

        // 测试1
        //test1_bfs();
        test2();
    }

    static test1() {
        def uf = new UF(10);
        println uf.connected(1, 2)

        uf.union(1, 2);
        println uf.connected(1, 2)
        println uf.count()
    }

    static test2() {
        def uf = new QuickUF(10);
        uf.union(0, 1);
        uf.union(1, 2);
    }
}
