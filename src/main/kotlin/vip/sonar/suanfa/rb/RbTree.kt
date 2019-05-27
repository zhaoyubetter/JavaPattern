package vip.sonar.suanfa.rb


/**
 * @description: 红黑树
 * @author better
 * @date 2019-04-20 15:34
 *
 *

参考：
红黑树Java代码实现 --- 非常好
1. https://blog.csdn.net/eson_15/article/details/51144079
极客时间
2. https://time.geekbang.org/column/article/68976
在线动画
3. https://www.cs.usfca.edu/~galles/visualization/RedBlack.html

性质：
1.节点是红色或黑色。
2.根节点是黑色。
3.每个叶子节点都是黑色的空节点（NIL节点）。
4 每个红色节点的两个子节点都是黑色。(从每个叶子到根的所有路径上不能有两个连续的红色节点)
5.从任一节点到其每个叶子的所有路径都包含相同数目的黑色节点。


//// 插入：
1.红黑树的前提是二叉查找树，所以二叉查找树的知识可以用过来，比如插入
2.红黑树的插入节点必须是红色，插入时，红黑树会自动平衡
3.什么时候用变色(parent与uncle全红)，啥时候左旋(parent红，uncle黑，右孩子)，啥时候右旋(parent红，uncle黑，左孩子)
4.变色和旋转之间的先后关系可以表示为：变色->左旋->右旋(变色可能没发生)；

/// 删除：节点
1. 删除操作平衡调整分为2步：
a.保证整棵红黑树在一个节点删除后，仍满足最后一条定义，包含相同数量的黑色节点；
b.针对关注节点进行二次调整，满足第4条，不存在相邻的2个红色节点；

节点删除后的顶替节点，如果顶替节点原来是红色，那么现在是红+黑，如果原来是黑色，那么它现在的颜色是黑+黑;

第一次调整：
case 1: 如果要删除的节点是a，且只有一个子节点b：
a.删除节点a，并把b替换到节点a的位置；

 */

/**
 * https://blog.csdn.net/lenfranky/article/details/89645755
 */
private class TreeOperation {
    companion object {

        val map = mutableMapOf<Int, RBNode<*>?>()

        private fun <T : Comparable<T>> getDeep(root: RBNode<T>?): Int {
            return if (root == null) 0 else 1 + Math.max(getDeep(root.left), getDeep(root.right))
        }

        fun <T : Comparable<T>> show(root: RBNode<T>?) {
            root?.apply {
                val treeDeep = getDeep(root)
                // 最后一行的宽度为2的（n - 1）次方乘3，再加1
                // 作为整个二维数组的宽度
                val arrayHeight = treeDeep * 2 - 1
                val arrayWidth = (2 shl treeDeep - 2) * 3 + 1
                // 用一个字符串数组来存储每个位置应显示的元素
                val res = Array(arrayHeight) { Array(arrayWidth) { " " } }

                // 从根节点开始，递归处理整个树
                // res[0][(arrayWidth + 1)/ 2] = root.data.toString();
                writeArray(this, 0, arrayWidth / 2, res, treeDeep)

                // 此时，已经将所有需要显示的元素储存到了二维数组中，将其拼接并打印即可
                res.forEach { line ->
                    line.forEach {
                        var value = it
                        try {
                            // 判断是红还是黑哈
                            val node = map[it.toInt()] as? RBNode<Int>
                            node?.apply {
                                value = if (this.isRed) "\u001B[31m${this.data}\u001B[0m" else this.data.toString()
                            }
                        } catch (e: Exception) {

                        }
                        print(value)
                    }
                    println()
                }
            }
        }

        fun <T : Comparable<T>> writeArray(currNode: RBNode<T>?, rowIndex: Int, colIndex: Int, res: Array<Array<String>>, treeDeep: Int) {
            if (currNode == null) {
                return
            }
            map[currNode.data as Int] = currNode as? RBNode<Int>
            val currNode = currNode!!

            // 先将当前节点保存到二维数组中
            res[rowIndex][colIndex] = (currNode.data).toString()
            // 计算当前位于树的第几层
            val currLevel = (rowIndex + 1) / 2
            // 若到了最后一层，则返回
            if (currLevel == treeDeep) return
            // 计算当前行到下一行，每个元素之间的间隔（下一行的列索引与当前元素的列索引之间的间隔）
            val gap = treeDeep - currLevel - 1

            // 对左儿子进行判断，若有左儿子，则记录相应的"/"与左儿子的值
            if (currNode.left != null) {
                res[rowIndex + 1][colIndex - gap] = "/"
                writeArray(currNode.left, rowIndex + 2, colIndex - gap * 2, res, treeDeep)
            }

            // 对右儿子进行判断，若有右儿子，则记录相应的"\"与右儿子的值
            if (currNode.right != null) {
                res[rowIndex + 1][colIndex + gap] = "\\"
                writeArray(currNode.right, rowIndex + 2, colIndex + gap * 2, res, treeDeep)
            }
        }
    }
}

private class RBNode<T : Comparable<T>>(
        var data: T
) {
    var parent: RBNode<T>? = null
    var left: RBNode<T>? = null
    var right: RBNode<T>? = null
    var isRed: Boolean = false
}

private class RBTree<T : Comparable<T>> {

    var rootNode: RBNode<T>? = null

    fun insert(data: T) {
        // 新插入的节点是红色
        val node = RBNode(data).apply { this.isRed = true }
        var parent: RBNode<T>? = rootNode
        var cNode: RBNode<T>? = parent

        // 查找位置 => parent
        while (cNode != null) {
            parent = cNode
            cNode = if (cNode.data > data) {
                cNode.left
            } else {
                cNode.right
            }
        }

        // 设置parent
        node.parent = parent

        // 判断插入到左还是右
        if (parent != null) {
            if (parent.data > node.data) {
                parent.left = node
            } else {
                parent.right = node
            }
        } else {  // 赋值根节点
            rootNode = node
        }

        // 调整树，满足红黑树特性
        insertFix(node)
    }

    /**
     * 插入修正
     * 1.插入节点的父节点和其叔叔节点（祖父节点的另一个子节点）均为红色的；
     *   => 将当前节点的父节点和叔叔节点涂黑，将祖父节点涂红，当前节点(current)指向祖父节点，再判断
     * 2.插入节点的父节点是红色，叔叔节点是黑色，且插入节点是其父节点的右子节点；
     *   => 将当前节点的父节点作为新的节点(N)，以N为支点做左旋操作，后将父节点和当前结点调换一下，为下面右旋做准备
     * 3.插入节点的父节点是红色，叔叔节点是黑色，且插入节点是其父节点的左子节点。
     * @param  addNode 待添加的节点（红色）
     */
    private fun insertFix(addNode: RBNode<T>) {
        var parent: RBNode<T>?        // 父节点
        var gParent: RBNode<T>         // 祖父节点
        var current: RBNode<T> = addNode    // 当前节点
        parent = current.parent

        // 需要fix条件： 父节点存在，且父节点是红色
        while (parent != null && parent?.isRed) {
            gParent = parent!!.parent!!       // 肯定存在祖父节点

            if (gParent?.left == parent) {
                val uncle = gParent?.right  // 获取叔叔节点

                // === case 1: 父节点是红色，uncle 也是红色
                if (uncle != null && uncle.isRed) {
                    // 将父与叔叔染黑，并将祖父节点涂红，并继续向上找到
                    parent?.isRed = false
                    uncle.isRed = false
                    gParent.isRed = true
                    current = gParent           // 当前节点指向祖父节点
                    parent = current.parent
                    continue                    // 继续循环，重新判断（注意这里是continue，没必须进入下面的case2，case3的判断分支）
                }

                // === case 2: 父节点是红的，uncle 黑色，且当前节点父节点的右孩子，旋转跟uncle没关系，这里就不需要判断uncle是否为null
                if (current == parent?.right) {
                    // 将当前节点的父节点作为新的节点(记作：N)，以N为支点做左旋操作，将父节点和当前结点调换一下，为下面右旋做准备
                    rotateLeft(current?.parent!!)       // 父节点处左旋,
                    val tmp = parent!!      // 交换当前节点与父节点
                    parent = current
                    current = tmp
                }

                // === case 3: 父红，uncle黑，且current是父的左孩子
                // 将当前的父parent涂黑，并将祖父节点涂红，并在祖父节点为支点做右旋操作。最后把根节点涂黑
                parent?.isRed = false
                gParent.isRed = true
                rotateRight(gParent)
            } else {  // 右孩子
                val uncle = gParent?.left  // 获取叔叔节点

                // === case 1: 父节点是红色，uncle 也是红色
                if (uncle != null && uncle.isRed) {
                    // 将父与叔叔染黑，并将祖父染红，并继续向上找到
                    parent?.isRed = false
                    uncle.isRed = false
                    gParent.isRed = true
                    current = gParent           // 当前节点指向祖父节点
                    parent = current.parent
                    continue                    // 继续循环，重新判断（注意这里是continue，没必须进入下面的case2，case3）
                }

                // === case 2: 父节点是红的，uncle 黑色，且当前节点父节点的左孩子
                if (current == parent?.left) {
                    // 将当前节点的父节点作为新的节点(记作：N)，以N为支点做右旋操作，将父节点和当前结点调换一下，为下面左旋做准备
                    rotateRight(parent!!)         // 父节点处右旋
                    val tmp = parent!!
                    parent = current
                    current = tmp
                }

                // === case 3: 父红，uncle黑，且current是父的右孩子
                // 将current涂黑，将祖父节点涂红，并在祖父节点为支点做左旋操作。最后把根节点涂黑
                parent?.isRed = false
                gParent?.isRed = true
                rotateLeft(gParent)
            }

            parent = current?.parent            // parent 重新赋值
        }

        this.rootNode?.isRed = false
    }

    /**
     * 左旋步骤
     * 1. 将y的左子节点赋给x的右子节点,并将x赋给y左节点的父节点(y左子节点非空时)
     * 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
     * 3. 将y的左子节点设为x，将x的父节点设为y
     */
    private fun rotateLeft(x: RBNode<T>) {
        val y = x.right
        x.right = y?.left

        if (y?.left != null) {
            // 1. 将y的左子节点赋给x的右子节点,并将x赋给y左节点的父节点(y左子节点非空时)
            y.left?.parent = x
        }

        // 2.将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
        y?.parent = x.parent

        if (x.parent == null) {  // x 为 根节点，更新rootNode
            this.rootNode = y
        } else {
            if (x == x.parent?.left) {       // x 为 左孩子
                x.parent?.left = y           // 则也将y设为左子节点
            } else {
                x.parent?.right = y
            }
        }

        // 3. 将y的左子节点设为x，将x的父节点设为y
        y?.left = x
        x.parent = y

    }

    /**
     * 右旋
     * 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
     * 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
     * 3. 将x的右子节点设为y，将y的父节点设为x
     */
    private fun rotateRight(y: RBNode<T>) {
        val x = y.left
        y.left = x?.right

        // 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
        if (x?.right != null) {
            x?.right?.parent = y
        }

        // 2.将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
        x?.parent = y.parent

        if (y.parent == null) {      // y 为 根节点，更新rootNode
            rootNode = x
        } else {
            if (y == y.parent?.left) {
                y.parent?.left = x
            } else {
                y.parent?.right = x
            }
        }

        // 3.将x的右子节点设为y，将y的父节点设为x
        x?.right = y
        y.parent = x
    }

    private fun search(data: T): RBNode<T>? {
        var cNode = rootNode
        while (cNode != null) {
            cNode = when {
                cNode!!.data > data -> cNode!!.left
                cNode!!.data < data -> cNode!!.right
                else -> {
                    return cNode
                }
            }
        }
        return null
    }


    /**
     * 删除节点
     *
     */
    fun delete(data: T) {
        val delNode = search(data)
        delNode?.let {
            deleteNode(it)
        }
    }

    private fun deleteNode(node: RBNode<T>) {
        var toRemove: RBNode<T> = node   // 待删除节点，待删节点有2个孩子时，toRemove会发生改变
        var child: RBNode<T>?            // 待删除节点的孩子（最多一个）
        var parent = toRemove.parent     // 待删节点parent(2个孩子时，parent会改变)

        /* === Part One: 获取前驱节点： 搬来2叉查找树的删除过程，情况2，删除后，是变成了情况1或者情况3 */
        if (node.left == null && node.right == null) {
            // 1.left与right都为null
            child = null
        } else if (node.left != null && node.right != null) {
            // 2.left与right都存在的情况，分为3个小步骤来操作
            // 2-a. 获取前驱节点 replace
            var replace = node.left
            while (replace?.right != null) {
                replace = replace.right
            }
            // 2-b.偷梁换柱
            node.data = replace?.data!!   // 直接改变值，完成替换，节点之前的关系保存不变
            child = replace.left          // 设置前驱节点值；这里不会存在右孩子，见上面while循环
            toRemove = replace            // 改变待删除节点；下面就变成了删除 前驱节点 了

            // 2-c.设置parent，待删节点变化了，所以待删节点parent也跟着变化
            parent = replace.parent
            if (parent == toRemove) {     // 前驱节点为待删节点的直接子孩子
                parent = replace
            }
        } else {
            // 3.只存在一个孩子节点时，那么待删除节点必须是黑色，child必须是红色（红黑特性）
            child = if (node.left != null) node.left else node.right
        }


        /* === Part Two: 删除toRemove节点 */
        // 待删除是根节点
        if (toRemove.parent == null) {
            // 删除是根节点，直接删除
            rootNode = null
            return
        }

        // 待删除非根节点时：先直接删除节点，并设置待删除节点孩子的父（注意：此时孩子只有一个）
        if (toRemove.parent?.left == toRemove) {
            toRemove.parent?.left = child
        } else {
            toRemove.parent?.right = child
        }

        // 如果孩子不能空，设置孩子的父
        if (child != null) {
            child.parent = toRemove.parent
        }

        /* === Part Three: 删除后变换 */
        if (child != null) {
            // 如果有child，那么toRemove必定是黑色，child必定是红色(红黑特性)，将child直接染黑，调整结束；
            child.isRed = false
            return  // 后面的判断没必要了，所以加return；不加也没事
        }

        // -> 待删除toRemove是黑色，并且孩子也是黑色的[没有孩子时，虚拟孩子的节点是黑色的]
        // 最麻烦的事情终于来了，😭😭😭😭😭😭😭😭😭，共分为4个情况
        if (!toRemove.isRed && (child == null || !child.isRed)) {
            removeFixUp(child, parent)  // 将后继节点的child和parent传进去
        }
    }

    //node表示关注节点(黑)，即前驱节点的子节点（因为前驱节点被挪到删除节点的位置去了）
    private fun removeFixUp(node: RBNode<T>?, parent: RBNode<T>?) {
        var node = node
        var parent = parent
        var other: RBNode<T>?   // 兄弟节点


        // (node 为 null || 是黑色) && node 不是根节点
        while ((node == null || !node.isRed) && (node != rootNode)) {
            if (parent?.left == node) {      // node 是左孩子，下面的 else 对称
                other = parent?.right        // 兄弟节点

                // === case 1: node 的兄弟节点是红色
                // a. other 染黑，parent 染红
                // b. 以 parent 左旋
                // c. 关注节点不变，other 兄弟节点重新赋值
                // d. 继续从4种情况中选择合适的规则来调整；
                if (other?.isRed == true) {
                    other.isRed = false
                    parent?.isRed = true
                    rotateLeft(parent!!)
                    other = parent?.right  // 兄弟节点重新赋值
                }

                // === case 2: node的兄弟节点other是黑色的，且other的两个子节点也都是黑色的
                // a. 关注节点的兄弟节点 other 染红;
                // b. 关注节点改成 parent，parent重新赋值
                // c. 如果还未完成，继续从4种情况中选择合适的规则来调整；
                if ((other?.left == null || other?.left?.isRed == false) &&
                        (other?.right == null || other?.right?.isRed == false)) {
                    other?.isRed = true
                    node = parent
                    parent = node?.parent   // parent 重新赋值
                } else {
                    // === case 3: node的兄弟节点other是黑色的，且other的左子节点是红色，右子节点是黑色(null 也是黑色)，以右为主判断
                    // a. 兄弟节点other的左孩子染黑，other染红；
                    // b. 围绕兄弟节点other右旋；
                    // c. 关注节点不变
                    // d. 跳转到case4，继续判断
                    if (other.right == null || other?.right?.isRed == false) {
                        other.left?.isRed = false
                        other.isRed = true
                        rotateRight(other)
                        other = parent?.right
                    }

                    // === case 4: node的兄弟节点other是黑色的，且other的右子节点是红色，左子节点颜色随意
                    // a. 将other设为node父节点相同的颜色
                    // b. parent设置黑色,other的右孩子设为黑色
                    // c. 围绕父节点左旋
                    // d. 调整结束
                    other?.isRed = parent?.isRed!!
                    parent?.isRed = false
                    other?.right?.isRed = false
                    rotateLeft(parent!!)

                    node = rootNode
                    break
                }
            } else {  // node 是右孩子，与上面的反过来
                other = parent?.left       // 兄弟节点

                // === case 1: node 的兄弟节点是红色
                // a. other 染黑，parent 染红
                // b. 以 parent 右旋
                // c. 关注节点不变，other 兄弟节点重新赋值
                // d. 继续从4种情况中选择合适的规则来调整；
                if (other?.isRed == true) {
                    other.isRed = false
                    parent?.isRed = true
                    rotateRight(parent!!)
                    other = parent?.left       // 为下一次做准备
                }

                // === case 2: node的兄弟节点other是黑色的，且other的两个子节点也都是黑色的
                // a. 关注节点的兄弟节点 other 染红;
                // b. 关注节点改成 parent，parent重新赋值
                // c. 继续从4种情况中选择合适的规则来调整；
                if ((other?.left == null || other?.left?.isRed == false) &&
                        (other?.right == null || other?.right?.isRed == false)) {
                    other?.isRed = true
                    node = parent
                    parent = node?.parent   // parent 重新赋值
                } else {
                    // === case 3: node的兄弟节点other是黑色的，且other的右子节点是红色，左子节点是黑色(null 也是黑色),以左为准
                    // a. 兄弟节点other的右孩子染黑，other染红；
                    // b. 围绕兄弟节点other左旋；
                    // c. 关注节点不变
                    // d. 跳转到case4，继续判断
                    if (other.left == null || other.left?.isRed == false) {
                        other.right?.isRed = false
                        other.isRed = true
                        rotateLeft(other)
                        other = parent?.left
                    }

                    // === case 4: node的兄弟节点other是黑色的，且other的左子节点是红色，右子节点黑或null
                    // a. 将other设为node父节点相同的颜色
                    // b. parent设置黑色,other的左孩子设为黑色
                    // c. 围绕父节点右旋
                    // d. 调整结束
                    other?.isRed = parent?.isRed!!
                    parent?.isRed = false
                    other?.left?.isRed = false
                    rotateRight(parent!!)

                    node = this.rootNode
                    break
                }
            }
        }
        if (node != null) {
            node.isRed = false
        }
    }
}


fun main() {

    // === 测试
    //  兄弟节点是红色节点
//    repairWhenOtherIsRed()

    //  other黑，other左红，右黑
//    repairWhenOtherBlackLeftRedAndRightBlack()

    // 测试删除
    //testDel()

    // 测试添加
    // testInsert1And3()
    testInert1And2()
}


/**
 * 测试添加节点，case 1, case 2,
 */
private fun testInert1And2() {
    val tree = RBTree<Int>()
    listOf(10, 7, 15, 30, 22, 23, 40, 34, 41).forEach {
        // 4, 3, 5, 14, 22, 15
        tree.insert(it)
        println("== insert node $it ")
        TreeOperation.show(tree.rootNode)
    }
}

/**
 * 测试添加节点, case 1, case 3
 */
private fun testInsert1And3() {
    val tree = RBTree<Int>()
    listOf(10, 7, 6, 5, 4, 3, 2, 3).forEach {
        // 4, 3, 5, 14, 22, 15
        tree.insert(it)
        println("== insert node $it ")
        TreeOperation.show(tree.rootNode)
    }
}


private fun testDel() {
    val tree = RBTree<Int>()
    tree.apply {
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
    }
    TreeOperation.show(tree.rootNode)
    listOf(7, 6, 3, 5, 4, 11, 14, 10, 8, 15, 22, 9).forEach {
        // 4, 3, 5, 14, 22, 15
        tree.delete(it)
        println("== del node $it ")
        TreeOperation.show(tree.rootNode)
    }
}

/**
 * 模拟数据调整变换情况之一
 * 1. 兄弟节点是红色节点情况
 */
private fun repairWhenOtherIsRed() {
    val tree = RBTree<Int>()
    tree.apply {
        listOf(7, 6, 9, 8, 10, 22).forEach {
            insert(it)
        }
    }
    TreeOperation.show(tree?.rootNode ?: null)
    // 删除节点6后，变成了：兄弟节点与其孩子全黑了；
    tree.delete(6)

    TreeOperation.show(tree?.rootNode ?: null)
}

/**
 * 模拟数据 调整变换情况之三
 * 2. other黑，other左红，右黑
 */
private fun repairWhenOtherBlackLeftRedAndRightBlack() {
    val tree = RBTree<Int>()
    tree.apply {
        listOf(7, 6, 9, 8).forEach {
            insert(it)
        }
    }
    TreeOperation.show(tree?.rootNode ?: null)
    tree.delete(6)
    TreeOperation.show(tree?.rootNode ?: null)
}


// https://www.cs.usfca.edu/~galles/visualization/RedBlack.html