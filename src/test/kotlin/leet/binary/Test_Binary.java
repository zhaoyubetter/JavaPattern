package leet.binary;

import com.better.Utils;
import org.junit.Before;
import org.junit.Test;
import vip.sonar.suanfa.binarytree.SearchBinaryTree2;

import static vip.sonar.suanfa.binarytree.SearchBinaryTree2.*;

public class Test_Binary {

    private Node root;

    @Before
    public void before() {
        root = new Node(1);
        root.insertNode(2, root);
        root.insertNode(5, root);
        root.insertNode(3, root);
        root.insertNode(4, root);
        root.insertNode(8, root);
        root.insertNode(6, root);
    }

    /**
     * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
     */

    Node pre = null;

    @Test
    public void test1() {
        preOrder(root);
        Utils.println(root.getRight());
    }

    private void preOrder(Node node) {
        if(node == null) {
            return;
        }
        preOrder(node.getRight());
        preOrder(node.getLeft());

        node.setRight(pre);
        node.setLeft(null);
        pre = node;
    }
}
