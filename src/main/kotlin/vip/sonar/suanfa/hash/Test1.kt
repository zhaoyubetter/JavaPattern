package vip.sonar.suanfa.hash

import java.util.LinkedHashMap

/**
 * @description: 散列
 * 装载因子：散列表的装载因子 = 填入表中的元素个数 / 散列表的长度
 * @author better
 * @date 2019-03-24 20:23
 * https://time.geekbang.org/column/article/64586
 */


/*
 jdk hashCode 代码
 hashCode 是一个32位的整数，对于不同的对象，需唯一

== 散列函数：
 hashMap 中的：
 static final int hash(Object key) {
       int h;
       // 先右移16位，再与本身进行异或，即：hashcode ^ (hashcode >>> 16)
       // 1. 将高16位无符号位移(高位补0)到低16位，也就是高16位全部是0
       // 2. 将自己的高位与地位异或获得结果，实际上高位还是保留了；

       return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
 }


 1.1 首先hashCode()返回值int最高是32位，如果直接拿hashCode()返回值作为下标，大概40亿的映射空间，
     只要哈希函数映射得比较均匀松散，一般是很难出现碰撞的。
     问题是一个40亿长度的数组，内存是放不下的。
 1.2 所以，用自己的高半区和低半区做异或，混合原始哈希码的高位和低位，
     关键是以此来加大低位的随机性。为后续计算index截取低位，保证低位的随机性。
     比如：
     原始: 1010 1011
     右移: 0000 1010
     异或: 1010 0001  结果
 1.3 这样设计保证了对象的hashCode的32位值只要有一位发生改变，整个hash()返回值就会改变，
     高位的变化会反应到低位里，保证了hash值的随机性。

 1.4 在插入或查找的时候，计算Key被映射到桶的位置：
    int index = hash(key) & (capacity - 1)



== String 类型的 hashCode 函数：
public int hashCode() {
    int h = hash;                      // 获取缓存值
    if (h == 0 && value.length > 0) {  // 没有缓存值
        char val[] = value;

        // s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
        // 31 的二进制是 0001 1111
        for (int i = 0; i < value.length; i++) {
            h = 31 * h + val[i];  // 字符串比较长时，会溢出，形成负数
        }
        hash = h;
    }
    return h;
}



 */
fun main() {
    val map = HashMap<String, String>()
    val str = java.lang.String("abcde")
    var h = 0
    str.forEach {
        h = (31 * h) + it.toInt()
    }
    println(h)


    val linkmap = LinkedHashMap<String, String>()

}