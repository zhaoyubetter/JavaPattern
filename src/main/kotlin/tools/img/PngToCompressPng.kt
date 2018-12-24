package tools.img

import java.io.File
import java.lang.Exception

/**
 * compressPng
 */
class PngToCompressPng : ImageTransform() {

    override fun transformOne(imageInfo: ImageInfo) {
        // use google to transform one
        val oldFile = File(imageInfo.filePath)
        var newFile = File(oldFile.parent, oldFile.name.substringBefore(".") + ".png")
        val cmd = "cwebp -q 75 ${oldFile.path} -o ${newFile.path}"
        try {
            cmd.runCommand(PngToWebp.GOOGLE_WEBP_TOOLS_DIR, PngToWebp.TIME_OUT)
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