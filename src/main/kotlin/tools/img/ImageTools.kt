package tools.img

import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO


// 测试代码
fun main(args: Array<String>) {
    /* cwebp -q 75  -o
    // /Users/zhaoyu1/1webpTest/drawable-xxhdpi/jdme_shape_wallet_title_outer_bg.png -o
    val path1 = "/Users/zhaoyu1/1webpTest/drawable-xxhdpi/jdme_shape_wallet_title_outer_bg.png"
    val path2 = "/Users/zhaoyu1/1webpTest/drawable-xxhdpi/11_cmd.webp"
    println("cwebp -q 75 $path1 -o $path2".runCommand("/Users/zhaoyu1/Documents/thin/webap/libwebp-1.0.1-rc2-mac-10.13/bin", 30))
    */

    /*
    val imgPath = "/Users/zhaoyu1/1webpTest/drawable-xxhdpi"
    getImageList(imgPath).forEach {
        println("转换:${it.name}")
        transformByShell(it)
        println("${it.name} ${it.prettyOriginalSize()} --> ${it.prettyTransformedSize()}")
    }
    */

    /* // pngToWeb */
    val imgPath = "/Users/zhaoyu1/1webpTest/drawable-xxhdpi"
    PngToWebp(isOver17 = false).transform(imgPath, suffixs = arrayOf(".png"), excludeSuffixs = arrayOf(".9.png"))


    /* // jpgToWeb
    val imgPath = "/Users/zhaoyu1/1webpTest/jpg"
    JpgToWebp().transform(imgPath, suffixs = arrayOf(".jpg", ".jpeg"), excludeSuffixs = arrayOf(".9.png"))
*/
}

sealed class ImageFormat {
    object Unkown : ImageFormat()
    object Jpeg : ImageFormat()
    class Png(var hasAlpha: Boolean = false) : ImageFormat()
    object Webp : ImageFormat()
}

data class ImageInfo(val name: String,
                     val filePath: String,
                     val originalSize: Long,
                     var format: ImageFormat = ImageFormat.Unkown,
                     var transformedSize: Long = 0,
                     var transformedFormat: ImageFormat? = null) {

    fun prettyOriginalSize(shorter: Boolean = false) = prettySize(originalSize, shorter)
    fun prettyTransformedSize(shorter: Boolean = false) = prettySize(transformedSize, shorter)
    private fun prettySize(size: Long, shorter: Boolean = false): String {
        var result = size * 1.0f
        var suffix = "B"
        if (result > 900) {
            suffix = "KB"
            result /= 1024
        }
        if (result > 900) {
            suffix = "MB"
            result /= 1024
        }
        if (result > 900) {
            suffix = "GB"
            result /= 1024
        }

        val value = when {
            result < 1 -> String.format("%.2f", result)
            result < 10 -> if (shorter) {
                String.format("%.1f", result)
            } else {
                String.format("%.2f", result)
            }
            result < 100 -> if (shorter) {
                String.format("%.0f", result)
            } else {
                String.format("%.2f", result)
            }
            else -> String.format("%.0f", result)
        }
        return "$value$suffix"
    }
}

internal fun String.runCommand(workingDir: String = ".",
                               timeoutAmount: Long = 30,
                               timeoutUnit: TimeUnit = TimeUnit.SECONDS): String? {
    return try {
        ProcessBuilder(*this.split("\\s".toRegex()).toTypedArray())
                .directory(File(workingDir))
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start().apply {
                    waitFor(timeoutAmount, timeoutUnit)
                }.inputStream.bufferedReader().readText()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}


/** 获取图片格式 */
internal fun getImageFormat(filePath: String): String? {
    ImageIO.getImageReaders(ImageIO.createImageInputStream(filePath)).next()?.let {
        return it.formatName
    }
    return null
}


/** 获取图片透明度 */
internal fun hasAlpha(filePath: String): Boolean {
    val img = ImageIO.read(File(filePath))
    return img?.colorModel?.hasAlpha() ?: false
}
