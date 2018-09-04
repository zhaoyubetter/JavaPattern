package kotlin_operator

import java.io.File
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaType

data class PhoneGoods(
        var brand: String? = null,
        var mode: String? = null,
        var price: Double? = null,
        var memory: String? = null,
        var storage: String? = null,
        var pixel: String? = null,
        var color: String? = null,
        var screenSize: Double? = null,
        var sellTime: String? = null,
        var sellCount: Int? = null
)

/**
 * 需求：
 * 1.获取销量前三的手机品牌与型号
 * 2.获取手机价格大于等于 3000 小于等于 5000的手机品牌与型号
 * 3.获取手机价格大于等于 3000 小于等于 5000的销量前3的手机品牌与型号，与销售数量
 * 4.根据手机品牌分组，算出每个品牌销售量第一的手机型号，与最后一名的型号
 * 5.根据手机品牌分组，返回所有价格 >= 3000 的手机
 * 6.根据手机品牌分组，返回各个品牌的总营业额，并按倒序排序
 */

fun main(args: Array<String>) {
    val phones = getPhoneGoods()

    println("\n======== 获取销量前三的手机品牌与型号 ========================")
    phones.sortedByDescending { it.sellCount }.take(3).forEachIndexed { index, it ->
        println("Number ${index + 1}, 品牌：${it.brand}, 型号：${it.mode}, 销量：${it.sellCount}")
    }

    println("\n======== 获取手机价格大于 3000 小于 5000的手机品牌与型号 ========================")
    phones.filter { it.price?.compareTo(3000) ?: 0 >= 0 && it.price?.compareTo(5000) ?: 0 <= 0 }.forEach {
        println("品牌：${it.brand}, 型号：${it.mode}, 价格：${it.price}")
    }

    println("\n======== 获取手机价格大于 3000 小于 5000的销量前3的手机品牌与型号 ========================")
    phones.filter { it.price?.compareTo(3000) ?: 0 >= 0 && it.price?.compareTo(5000) ?: 0 <= 0 }.sortedByDescending { it.sellCount }.take(3)
            .forEach {
                println("品牌：${it.brand}, 型号：${it.mode}, 价格：${it.price}, 销量: ${it.sellCount}")
            }


    // =========== groupBy 与 flatMap 综合运用================================
    println("\n======== 根据手机品牌分组，算出每个品牌销售量第一的手机型号 ========================")
    phones.sortedByDescending { it.sellCount }.groupBy { it.brand }.flatMap { it.value.take(1) }.forEach {
        println("top one 品牌:${it.brand}, 型号：${it.mode}, 销量：${it.sellCount}")
    }

    println("\n======== 根据手机品牌分组，返回所有价格大于3000 的手机 ========================")
    phones.sortedByDescending { it.sellCount }.groupBy { it.brand }.flatMap { it.value.filter { it.price?.compareTo(3000) ?: 0 >= 0 } }.forEach {
        println("品牌：${it.brand}, 型号：${it.mode}, 价格：${it.price}")
    }

    println("\n======== 根据手机品牌分组，返回各个品牌的总营业额 ========================")
    phones.groupBy { it.brand }.flatMap { it ->
        listOf(Pair(it.key, it.value.sumByDouble { it.price ?: 0.0 }))
    }.sortedByDescending { it.second }.forEach {
        println("品牌：${it.first}, 营业额：${it.second}")
    }
}


// ========= 以下为获取数据源的方法 ===========================================================
internal fun getPhoneGoods(): List<PhoneGoods> {
    var result: List<PhoneGoods> = ArrayList()
    val filePath = PhoneGoods::class.java.getResource("../operator_test.txt").path
    // 序号与属性
    var propsMap: Map<Int, KProperty1<PhoneGoods, *>>? = null

    File(filePath)?.run {
        if (exists()) {
            readLines().forEachIndexed { index, s ->
                if (index == 0) {        // 0行
                    propsMap = getKPropertyMap(s)
                } else {
                    val propValues = s.split(",")
                    result += getOnePhoneGoods(propsMap, propValues)
                }
            }
        }
    }
    return result
}

private inline fun getKPropertyMap(s: String): Map<Int, KProperty1<PhoneGoods, *>> {
    var propsMap = mutableMapOf<Int, KProperty1<PhoneGoods, *>>()
    s.split(",").forEachIndexed { i, propName ->
        propsMap[i] = PhoneGoods::class.memberProperties.first { it.name == propName }
    }
    return propsMap.toMap()
}

private inline fun getOnePhoneGoods(propMap: Map<Int, KProperty1<PhoneGoods, *>>?, propValues: List<String>): PhoneGoods {
    val phone = PhoneGoods()
    propMap?.forEach { i, it ->
        if (it is KMutableProperty1) {
            when (it.returnType.javaType) {
                java.lang.String::class.java -> {
                    it as KMutableProperty1<PhoneGoods, String?>
                    it.set(phone, propValues[i])
                }
                java.lang.Integer::class.java -> {       // 默认是Integer
                    it as KMutableProperty1<PhoneGoods, Int?>
                    it.set(phone, propValues[i].toIntOrNull())
                }
                java.lang.Double::class.java -> {        // 默认是double
                    it as KMutableProperty1<PhoneGoods, Double?>
                    it.set(phone, propValues[i].toDoubleOrNull())
                }
            }
        }
    }
    return phone
}