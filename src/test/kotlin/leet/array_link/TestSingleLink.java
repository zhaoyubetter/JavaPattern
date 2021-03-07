package leet.array_link;

import com.better.Utils;
import org.junit.Before;
import org.junit.Test;

import java.util.PriorityQueue;

import static vip.sonar.suanfa.link.two.Test1_revert.*;

public class TestSingleLink {

    private SingleLink link = new SingleLink();

    @Before
    public void before() {
        link.insertLast("A");
        link.insertLast("B");
        link.insertLast("C");
        link.insertLast("D");
        link.insertLast("E");
        link.insertLast("F");
        link.insertLast("G");
    }

    /**
     * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
     k 是一个正整数，它的值小于或等于链表的长度。
     如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
      

     示例：
     给你这个链表：1->2->3->4->5
     当 k = 2 时，应当返回: 2->1->4->3->5
     当 k = 3 时，应当返回: 3->2->1->4->5
     来源：力扣（LeetCode）
     链接：https://leetcode-cn.com/problems/reverse-nodes-in-k-group
     著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

     思路：
     分组反转，并实现衔接，衔接的过程特别麻烦，很容易出错
     ===========
     比如(k为3时):
     A->B->C->D->E->F->G
     可分为3组：
     A->B->C | D->E->F | G
     设置变量 prevLast，curLast，prevLast 为上一组的 last，curLast 为当前组的 last。
     当开始时：让 prevLast 指向当前组的 first，即：cur，设置 curLast 为 first，即：cur；

     1. 第一组反转后：
     C->B->A         D->E->F->G
     2. 让上一组的最后一个元素 prevLast，这里为 A 的 next 指向后续链表的第一个节点，即：
     C->B->A->|D->E->F->G
     3. 结果开始第二组的反转，结果如下：
     C->B->A->D  | F->E->D
     4. 可以看到，我们需要将 A （prevLast）指向到 F（组的第一个节点） 即可组的完成衔接，所以在新组遍历结束时，可以获取到最后节点，
     让 prevLast.next = 当前组的最后节点，同时更新 prevLast，让其指向 D，然后继续执行第一步；
     C->B->A-> |F->E->D|
     5. 最后一组的处理，这里需要判断一下，个数是否大于 k，如没有直接衔接返回；
     */
    @Test
    public void test1() {
        printList(null);
        Node node = reverseKGroup(link.getHead().getNext(), 3);
        printList(node);
    }

    /**
     * 改进版本，简单易懂，与自己考虑的思路实际是一样的，但少了很多判断
     * 链表分区为已翻转部分+待翻转部分+未翻转部分
     */
    @Test
    public void test2() {
        printList(null);
        Node node = reverseKGroup2(link.getHead().getNext(), 3);
        printList(node);
    }

    private Node reverseKGroup2(Node head, int k) {
        Node dummy = new Node(0);
        dummy.setNext(head);
        Node pre = dummy;
        Node end = dummy;

        while (end.getNext() != null) {
            for (int i = 0; i < k && end != null; i++) {
                end = end.getNext();
            }
            if (end == null) {
                break;
            }

            Node next = end.getNext();
            Node start = pre.getNext();
            end.setNext(null);
            Node tmp = reverseRange(start);    // 翻转[start,end]
            pre.setNext(tmp);       // 衔接

            start.setNext(next);
            pre = start;
            end = pre;  // 下一轮
        }

        return dummy.getNext();
    }

    private Node reverseRange(Node start) {
        Node prev = null;
        Node cur = start;
        while (cur != null) {
            Node next = cur.getNext();
            cur.setNext(prev);
            prev = cur;
            cur = next;
        }
        return prev;
    }

    private Node reverseKGroup(Node head, int k) {
        if (!linkSizeEnough(head, k)) {
            return head;
        }

        Node prev = null;
        Node cur = head;
        Node newHead = null;    // 返回的表头

        // 上一组的 last
        Node prevLast = null;
        // 当前组的 last
        Node curLast = null;

        // 每个组元素的下标
        int i = 0;
        while (cur != null) {
            // 1. 当开始时：让 prevLast 指向当前组的第一个节点，即：cur，设置 curLast 为 第一个节点，即：cur；
            if (i == 0) {
                if (prevLast != null) {
                    prevLast.setNext(cur);
                }
                curLast = cur;
            }

            // 2.反转 a->b->c => c->b->a
            Node tmp = cur.getNext();
            cur.setNext(prev);
            prev = cur;
            cur = tmp;

            // 3.当前组结算
            if (++i == k) {
                if (newHead == null) {   // 新的头
                    newHead = prev;
                }

                // 上组与当前组的衔接,最关键的3行
                if (prevLast != null) {
                    prevLast.setNext(prev); // 当前组最后节点
                }
                prevLast = curLast;
                prev = null;  // prev 归位

                // 不够，直接衔接剩余链表，退出
                if (!linkSizeEnough(cur, k)) {
                    prevLast.setNext(cur);
                    break;
                }

                // 4.进入下一组
                i = 0;
                continue;
            }
        }

        return newHead;
    }

    private boolean linkSizeEnough(Node cur, int k) {
        Node tmp = cur;
        boolean enough = false;
        int j = 0;
        while (tmp != null) {
            j++;
            if (j == k) {
                enough = true;
                break;
            }
            tmp = tmp.getNext();
        }
        return enough;
    }


    private void printList(Node head) {
        if (head == null) {
            head = link.getHead().getNext();
        }
        Node cur = head;
        while (cur != null) {
            Utils.print(cur.getData() + "->");
            cur = cur.getNext();
        }
        Utils.println("\n");
    }
}
