package com.better.kotlin

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.io.*
import javax.xml.parsers.SAXParserFactory

//val folderPath = ""
val folderPath = ""
val xmlFile = ""
val decryptFile = "test.dat"

// 临时
val resultFileOutPath = ""

fun main(args: Array<String>) {
    //encrypt()
    decrypt()
}

/*------ decrypt   ----------------------------------------------------------*/
fun decrypt() {
    val file = File("$folderPath/$decryptFile")
    val ips = FileInputStream(file)

    // 1.解包头信息
    val bytes = ByteArray(2)
    ips.read(bytes, 0, 2)
    println("decrypt itemCount: ${byteArray2Short(bytes)}")
    println("decrypt stringColumnSize: ${ips.read()}")

    // 2.解包数据
    val stringColumns = listOf("name", "uk_phonetics", "us_phonetics", "description")
    val stringColumnSize = stringColumns.size

    val dataColumns = listOf("uk_sound", "us_sound", "picture")
    val dataColumnSize = dataColumns.size

    val map = mutableMapOf<Int, String>()
    listOf(stringColumns, dataColumns).flatMap { it }.forEachIndexed { index, value: String ->
        map[index] = value
    }

    // 3.解包文本 与 文件
    while (true) {
        val wordItem = WordItemInfo()

        var readLength = 0    // 当前读的大小
        val totalSize = stringColumnSize + dataColumnSize
        for (i in (0 until totalSize)) {
            val property = map[i]
            if (i < stringColumnSize) {             // string
                val sizeBytes = ByteArray(2)
                readLength = ips.read(sizeBytes, 0, 2)
                if (readLength > 0) {
                    wordItem::class.java.getDeclaredField(property).apply {
                        isAccessible = true
                        set(wordItem, decryptString(ips, sizeBytes))
                    }
                }
            } else {                                // 文件
                val sizeBytes = ByteArray(4)
                readLength = ips.read(sizeBytes, 0, 4)
                if (readLength > 0) {
                    val fileBytes = decryptFile(ips, sizeBytes)
                    if (fileBytes != null) {
                        // 输出测试，TODO:   注意。。。。。
                        if (property == "picture") {
                            File("$resultFileOutPath/${wordItem.name}.jpq").writeBytes(fileBytes)
                        } else {
                            File("$resultFileOutPath/${wordItem.name}_$property.wav").writeBytes(fileBytes)
                        }
                    }
                }
            }
        }
        if (readLength <= 0) {
            break
        }
    }

}

inline fun decryptString(ips: InputStream, sizeByte: ByteArray): String? {
    val contentSize = byteArray2Short(sizeByte)
    if (contentSize > 0) {              // 有数据
        val dataBytes = ByteArray(contentSize)
        ips.read(dataBytes, 0, contentSize)
        return String(dataBytes)
    }
    return null
}

inline fun decryptFile(ips: InputStream, sizeByte: ByteArray): ByteArray? {
    val fileSize = byteArray2Int(sizeByte)
    if (fileSize > 0) {
        val dataBytes = ByteArray(fileSize)
        ips.read(dataBytes, 0, fileSize)
        return dataBytes
    }
    return null
}

/*------ encrypt  ----------------------------------------------------------*/
fun encrypt() {
    val file = File("$folderPath/$xmlFile")

    //
    /* ------- 1.获取bean信息 ------- */
    val wordItems = mutableListOf<WordItemInfo>()
    SAXParserFactory.newInstance().newSAXParser()
            .parse(file, object : DefaultHandler() {
                override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
                    if ("item" == qName && attributes != null) {
                        val item = WordItemInfo()
                        wordItems.add(item)
                        for (i in 0 until attributes.length) {
                            val propName = attributes.getLocalName(i)
                            item::class.java.getDeclaredField(propName).apply {
                                isAccessible = true
                                set(item, attributes.getValue(i))
                            }
                        }
                        item.picture = item.name + ".jpg"
                    }
                }
            })

    println("word size: ${wordItems.size}")

    /* ------- 2.开始写文件 ------- */
    val outputStream = FileOutputStream(File(folderPath, decryptFile))
    val stringColumns = listOf("name", "uk_phonetics", "us_phonetics", "description")
    val dataColumns = listOf("uk_sound", "us_sound", "picture")
    // a.写头信息
    outputHeader(outputStream, wordItems, stringColumns)
    // b.写入数据
    outputData(outputStream, wordItems, stringColumns, dataColumns)

    outputStream.flush()
    outputStream.close()
}

inline fun outputHeader(os: OutputStream, workItems: List<WordItemInfo>, stringColumns: List<String>) {
    val itemSize = workItems.size       // 个数
    os.write(short2Byte(itemSize))          // 2个字节，如果限制大小
    os.write(stringColumns.size)            // 1个字节
}

inline fun outputData(os: OutputStream, workItems: List<WordItemInfo>, stringColumns: List<String>, dataColumn: List<String>) {
    workItems.forEach { item ->
        // 字符
        stringColumns.forEach { column ->
            val field = item::class.java.getDeclaredField(column)
            field.isAccessible = true
            outputString(os, field.get(item)?.toString())
        }
        // 文件
        dataColumn.forEach { column ->
            val field = item::class.java.getDeclaredField(column)
            field.isAccessible = true
            outputFile(os, field.get(item)?.toString())
        }
    }
}

inline fun outputString(os: OutputStream, content: String?) {
    if (content != null) {
        val dataBytes = content.toByteArray()
        os.write(short2Byte(dataBytes.size))                  // 写入content长度，2个字节
        os.write(dataBytes)                                   // 写入数据
    } else {
        os.write(short2Byte(0))                           // 写入2个字节占位，TODO:// 不知道有啥更好的办法节约存储
    }
}

inline fun outputFile(os: OutputStream, fileName: String?) {
    if (fileName != null) {
        val file = File(folderPath, fileName)
        if (file.exists()) {     // 文件长度用4个字节吧
            val fileBytes = file.readBytes()
            os.write(int2ByteArray(fileBytes.size))
            os.write(fileBytes)
        } else {
            os.write(int2ByteArray(0))               // TODO:// 哎，这里要废4个字节啊，，，不知道有啥更好的办法节约存储
        }
    } else {
        os.write(int2ByteArray(0))                   // TODO:// 哎，这里要废4个字节啊，，，不知道有啥更好的办法节约存储
    }
}


/*------ tools method  ----------------------------------------------------------*/
/** java中int是4个字节，共32位
将int转为低字节在前，高字节在后的byte数组
b[0] = 11111111(0xff) & 01100001
b[1] = 11111111(0xff) & (n >> 8)00000000
b[2] = 11111111(0xff) & (n >> 8)00000000
b[3] = 11111111(0xff) & (n >> 8)00000000
 */
inline fun int2ByteArray(a: Int): ByteArray {
    return byteArrayOf(
            (a shr 24 and 0xFF).toByte(),
            (a shr 16 and 0xFF).toByte(),
            (a shr 8 and 0xFF).toByte(),
            (a and 0xFF).toByte())
}

inline fun byteArray2Int(b: ByteArray): Int {
    return b[3].toInt() and 0xFF or (
            b[2].toInt() and 0xFF shl 8) or (
            b[1].toInt() and 0xFF shl 16) or (
            b[0].toInt() and 0xFF shl 24)
}

inline fun byteArray2Short(b: ByteArray): Int {
    return b[0].toInt() and 0xff shl 8 or (b[1].toInt() and 0xff)
}

inline fun short2Byte(a: Int): ByteArray {
    return byteArrayOf((a.toInt() shr 8 and 0xFF).toByte(),
            (a.toInt() and 0xFF).toByte())
}

class WordItemInfo {
    lateinit var name: String
    var uk_phonetics: String? = null
    var us_phonetics: String? = null
    var uk_sound: String? = null
    var us_sound: String? = null
    var description: String? = null
    var picture: String? = null
}