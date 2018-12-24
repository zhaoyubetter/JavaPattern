package tools.img

import java.io.*
import java.lang.Exception

/**
 * png to webp.
 */
class PngToWebp(val isOver17: Boolean = false) : ImageTransform() {


    companion object {
        const val GOOGLE_WEBP_TOOLS_DIR = "/Users/zhaoyu1/Documents/thin/webap/libwebp-1.0.1-rc2-mac-10.13/bin"
        const val TIME_OUT = 30L
    }

    override fun getImageInfoList(path: String, suffixs: Array<String>, excludeSuffixs: Array<String>?): List<ImageInfo> {
        var list = super.getImageInfoList(path, suffixs, excludeSuffixs)
        // 低于4.2版本，含有透明的png不能压缩，否则加载不出来
        return if (!isOver17) {
            list.filterNot {
                hasAlpha(it.filePath).apply {
                    log.info("===> ${it.name} has transparent then skipped! ")
                }
            }
        } else {
            list
        }
    }

    override fun transformOne(imageInfo: ImageInfo) {
        // use google to transform one
        val oldFile = File(imageInfo.filePath)
        var newFile = File(oldFile.parent, oldFile.name.substringBefore(".") + ".webp")
        val cmd = "cwebp -q 75 ${oldFile.path} -o ${newFile.path}"
        try {
            cmd.runCommand(GOOGLE_WEBP_TOOLS_DIR, TIME_OUT)
            imageInfo.transformedFormat = ImageFormat.Webp
            imageInfo.transformedSize = newFile.length()
            log.info("${imageInfo.name} Completed! ".padEnd(30, ' ') +
                    "OriginalSize: ${imageInfo.prettyOriginalSize()}, ".padEnd(25, ' ') +
                    "TransformedSize: ${imageInfo.prettyTransformedSize()}")
        } catch (e: Exception) {
            log.error(e)
        } finally {
            if (newFile.exists() && newFile.length() > oldFile.length()) {  // Big than old, then del.
                newFile.delete()
                log.info("====> ${imageInfo.name} 转换后，比源文件还大，放弃转换")
            }
        }
    }
}

/*
转换比较慢，可能卡死，可能是库问题，待跟进
fun transform(imageInfo: ImageInfo) {
    val oldFile = File(imageInfo.filePath)
    try {
        // 先往内存中写
        var bops = ByteArrayOutputStream()
        ImageIO.write(ImageIO.read(oldFile), "webp", bops)
        // 比源文件还大，返回
        if (bops.size() >= oldFile.length()) {
            println("====> ${imageInfo.name} 转换后，比源文件还大，不转换了。。。")
            return
        }

        var newFile = File(oldFile.parent, oldFile.name.substringBefore(".") + ".webp")
        bops.writeTo(FileOutputStream(newFile))
        bops.flush()
        imageInfo.transformedFormat = ImageFormat.Webp
        imageInfo.transformedSize = newFile.length()
    } catch (e: IOException) {
        System.err.println(e)
    }
}
*/





