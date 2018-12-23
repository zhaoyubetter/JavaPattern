package tools.img

import java.io.File

sealed class ImageFormat {
    object Jpeg : ImageFormat()
    class Png(val hasAlpha: Boolean) : ImageFormat()
    class Webp() : ImageFormat()
}

data class ImageInfo(val filePath: String, val oldSize: Long,
                     val format: ImageFormat, var tranSize: Int = 0)

/**
 * 图片转换
 */
fun main(args: Array<String>) {

}

fun getImageList() {
    val path = "/img/"
    val imgDir = ImageInfo::class.java.getResource(path).path
    val dir = File(imgDir)
    if (dir.exists()) {
        val imgs = dir.listFiles().filter { it.name.endsWith(".jpg") }
                .map {
                    ImageInfo(it.path, it.totalSpace, ImageFormat.Jpeg)
                }.toList()
    }
}

fun transform(imgList: List<ImageInfo>) {

}


