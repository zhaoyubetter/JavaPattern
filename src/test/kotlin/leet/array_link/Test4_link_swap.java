package leet.array_link;

import com.better.Utils;
import org.junit.Test;

import static vip.sonar.suanfa.link.two.Test1_revert.*;

import vip.sonar.suanfa.link.two.Test1_revert;

/**
 * 给你链表的头节点 head 和一个整数 k 。
 *
 * 交换 链表正数第 k 个节点和倒数第 k 个节点的值后，返回链表的头节点（链表 从 1 开始索引）。
 */
public class Test4_link_swap {
    @Test
    public void test1() {
        int k = 2;
        Test1_revert.SingleLink link = new Test1_revert.SingleLink();

        link.insertLast(1);
        link.insertLast(2);
//        link.insertLast(3);
//        link.insertLast(4);
//        link.insertLast(5);
//        link.insertLast(6);
//        link.insertLast(7);
//        link.insertLast(8);

        Test1_revert.Node head = link.getHead().getNext();
        printList(head);
        Node newNode = swapNodes(head, 2);
        printList(newNode);
    }

    public Test1_revert.Node swapNodes(Test1_revert.Node head, int k) {
        Node pre = new Node();
        pre.setNext(head);
        Node l = head;
        Node r = head;
        int i = 0;

        return pre.getNext();
    }

    private void printList(Test1_revert.Node head) {
        if (head == null) {
            return;
        }
        Test1_revert.Node cur = head;
        while (cur != null) {
            Utils.print(cur.getData() + "->");
            cur = cur.getNext();
        }
        Utils.println("\n");
    }

}
