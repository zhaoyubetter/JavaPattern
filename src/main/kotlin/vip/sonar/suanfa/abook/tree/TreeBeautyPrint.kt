package vip.sonar.suanfa.abook.tree

open class BaseNode<T : Comparable<T>>(var data: T) {
    var left: BaseNode<T>? = null
    var right: BaseNode<T>? = null
    var height: Int = 0
}

class TreeBeautyPrint {
    companion object {
        val map = mutableMapOf<Int, BaseNode<*>?>()
        private fun <T : Comparable<T>> getDeep(root: BaseNode<T>?): Int {
            return if (root == null) 0 else 1 + Math.max(getDeep(root.left), getDeep(root.right))
        }

        fun <T : Comparable<T>> writeArray(currNode: BaseNode<T>?, rowIndex: Int, colIndex: Int, res: Array<Array<String>>, treeDeep: Int) {
            if (currNode == null) {
                return
            }
            map[currNode.data as Int] = currNode as? BaseNode<Int>
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

        fun <T : Comparable<T>> show(root: BaseNode<T>?) {
            root?.apply {
                val treeDeep = getDeep(root)
                // 最后一行的宽度为2的（n - 1）次方乘3，再加1
                // 作为整个二维数组的宽度
                val arrayHeight = treeDeep * 2 - 1
                val arrayWidth = (2 shl treeDeep - 2) * 3 + 1
                // 用一个字符串数组来存储每个位置应显示的元素
                val res = Array(arrayHeight) { Array(arrayWidth) { " " } }

                // 从根节点开始，递归处理整个树
                // res[0][(arrayWidth + 1)/ 2] = tree.data.toString();
                writeArray(this, 0, arrayWidth / 2, res, treeDeep)

                // 此时，已经将所有需要显示的元素储存到了二维数组中，将其拼接并打印即可
                res.forEach { line ->
                    line.forEach {
                        var value = it
                        try {
                            // 判断是红还是黑哈
                            val node = map[it.toInt()]
                            node?.apply {
                                value = this.data.toString()
                            }
                        } catch (e: Exception) {

                        }
                        print(value)
                    }
                    println()
                }
            }
        }
    }
}