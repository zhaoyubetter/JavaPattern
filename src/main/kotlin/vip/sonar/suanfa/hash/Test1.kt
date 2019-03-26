package vip.sonar.suanfa.hash

/**
 * @description: 散列
 * 装载因子：散列表的装载因子 = 填入表中的元素个数 / 散列表的长度
 * @author better
 * @date 2019-03-24 20:23
 */
/*
 jdk hashCode 代码
 hashCode 是一个32位的整数，对于不同的对象，需唯一

== 散列函数：
 static final int hash(Object key) {
       int h;
       return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
 }

== String 类型的 hashCode 函数：
public int hashCode() {
    int h = hash;
    if (h == 0 && value.length > 0) {
        char val[] = value;

        for (int i = 0; i < value.length; i++) {
            h = 31 * h + val[i];
        }
        hash = h;
    }
    return h;
}



 */
fun main() {
    val map = HashMap<String, String>()
}