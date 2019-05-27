package vip.sonar.suanfa.utils

/**
 * @description: 树结构工具类
 * @author better
 * @date 2019-05-11 16:18
 */

// https://blog.csdn.net/lenfranky/article/details/89645755
fun main() {
    val a = "a"
    println("\u001B[31m$a\u001B[0m")
}

/*
// 1. 被删除的节点“左右子节点都不为空”的情况
        if (node.left != null && node.right != null) {
            // 先找到被删除节点的后继节点，用它来取代被删除节点的位置

            // a. 获取后继节点 (右)
            var replace = node.right
            while (replace?.left != null) {  // 如右孩子有左，不断获取左
                replace = replace.left
            }

            // b. 处理“后继节点”与“被删除节点的父节点”之间的关系
            // 首先删除节点，与二叉查找树无异
            if (node.parent != null) {  // 被删除节点不是根节点，将node，替换成后继节点
                if (node?.parent?.left == node) {
                    node?.parent?.left = replace
                } else {
                    node?.parent?.right = replace
                }
            } else {
                this.rootNode = replace
            }

            // c. 处理“后继节点的子节点”和“被删除节点的子节点”之间的关系
            // 注意：这里比二叉查找树关系处理复杂的多，这里的节点多了parent关系，需要相互指向，还有颜色的处理
            child = replace?.right       // 这里【不会存在左孩子】！
            color = replace?.isRed!!     // 记录后继节点的颜色
            parent = replace?.parent     // 后继节点父
            if (parent == node) {        // 后继节点是被删除节点的子节点
                parent = replace         // parent直接赋值给后继节点
            } else {
                // child 关系重新梳理
                if (child != null) {
                    child.parent = parent
                }
                parent?.left = child
                replace.right = node.right
                node?.right?.parent = replace
            }
            // replace父与左的关系重新梳理
            replace.parent = node.parent    // 指向“被删除节点”的parent
            replace.isRed = node.isRed      // 保持"被删除节点"原来的颜色
            replace.left = node.left        // 相互衔接
            node?.left?.parent = replace    // "被删除节点"左孩子设置新的parent

            // d.如果后继节点颜色是黑色，重新修整红黑树
            if (color) {
                removeFixUp(child, parent)  // 将后继节点的child和parent传进去
            }
        }
 */

/*
if (toRemove.isRed || (!toRemove.isRed && child?.isRed == true)) {
        if (toRemove?.parent?.left == toRemove) {
            toRemove?.parent?.left = child
        } else {
            toRemove?.parent?.right = child
        }
        // 设置孩子的父
        child?.let {
            it.parent = toRemove.parent
            if(it.isRed) {
                child.isRed = false   // 如果有孩子，并且孩子是红色，孩子染黑
            }
        }
    } else {
    }
 */
/*
  insert(8)
        insert(11)
        insert(7)
        insert(6)
        insert(5)
        insert(4)
        insert(3)
        insert(22)
        insert(10)
        insert(9)
        insert(15)
        insert(14)
 */