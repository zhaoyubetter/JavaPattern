package vip.sonar.suanfa.algorithm.dynamic.two;

public class Test {
    @org.junit.Test
    public void test() {
        minDistBT(0, 0, 0, w, 4);
        System.out.println("is" + minDist);
    }

    private int minDist = Integer.MAX_VALUE;
    private int[][] w = {
            {1, 3, 5, 9},
            {2, 1, 3, 4},
            {5, 2, 6, 7},
            {6, 8, 4, 3},
    };


    // 调用方式：minDistBacktracing(0, 0, 0, w, n);
    public void minDistBT(int i, int j, int dist, int[][] w, int n) {
        // 到达了n-1, n-1这个位置了，这里看着有点奇怪哈，你自己举个例子看下
        if (i == n && j == n) {
            if (dist < minDist) minDist = dist;
            return;
        }
        if (i < n) { // 往下走，更新i=i+1, j=j
            minDistBT(i + 1, j, dist + w[i][j], w, n);
        }
        if (j < n) { // 往右走，更新i=i, j=j+1
            minDistBT(i, j + 1, dist + w[i][j], w, n);
        }
    }
}
