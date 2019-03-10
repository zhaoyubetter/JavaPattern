package vip.sonar.suanfa.queue;

/**
 * @author better
 * @description: TODO
 * @date 2019-03-05 22:57
 */
public class CircleQuque {
    // 数组：items，数组大小：n
    private String[] items;
    private int n = 0;
    // head 表示队头下标，tail 表示队尾下标
    private int head = 0;
    private int tail = 0;

    // 申请一个大小为 capacity 的数组
    public CircleQuque(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    // 入队
    public boolean enqueue(String item) {
        // 队列满了
        if ((tail + 1) % n == head) return false;
        items[tail] = item;
        tail = (tail + 1) % n;
        return true;
    }

    // 出队
    public String dequeue() {
        // 如果 head == tail 表示队列为空
        if (head == tail) return null;
        String ret = items[head];
        head = (head + 1) % n;
        return ret;
    }

    public static void main(String... cc) {
        CircleQuque a = new CircleQuque(5);
        a.enqueue("a");
        a.enqueue("b");
        a.enqueue("c");
        a.enqueue("d");
        a.enqueue("e");

        int c = a.head;
    }

}
