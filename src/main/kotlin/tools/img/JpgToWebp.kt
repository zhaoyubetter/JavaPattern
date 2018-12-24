package tools.img

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.imageio.ImageIO

/**
 *
 * For jpeg
 */
class JpgToWebp : ImageTransform() {

    override fun transformOne(imageInfo: ImageInfo) {
        val oldFile = File(imageInfo.filePath)
        try {
            // 先往内存中写
            ByteArrayOutputStream().apply {
                ImageIO.write(ImageIO.read(oldFile), "webp", this)
                // 比源文件还大，返回
                if (this.size() >= oldFile.length()) {
                    log.info("====> ${imageInfo.name} 转换后，比源文件还大，不转换了")
                    return
                } else {
                    var newFile = File(oldFile.parent, oldFile.name.substringBefore(".") + ".webp")
                    this.writeTo(FileOutputStream(newFile))
                    this.flush()
                    imageInfo.transformedFormat = ImageFormat.Webp
                    imageInfo.transformedSize = newFile.length()
                    log.info("${imageInfo.name} Completed! ".padEnd(30, ' ') +
                            "OriginalSize: ${imageInfo.prettyOriginalSize()}, ".padEnd(25, ' ') +
                            "TransformedSize: ${imageInfo.prettyTransformedSize()}")
                }
            }
        } catch (e: IOException) {
            System.err.println(e)
        }
    }
}