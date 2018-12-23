package tools.img

import java.awt.image.BufferedImage
import java.awt.image.PixelGrabber
import java.io.File
import javax.swing.ImageIcon
import javax.imageio.ImageIO


fun main(args: Array<String>) {
    val path = """/Users/zhaoyu1/Documents/git.jd/WebpResolveDemo/app/src/main/res/mipmap-mdpi/ic_launcher.png"""
    val path2 = """/Users/zhaoyu1/Documents/git.jd/WebpResolveDemo/app/src/main/res/drawable/jpg_.jpg"""
//    println(hasAlpha(path2))
    println(hasAlpha2(path2))
}

@Deprecated(message = "deprecated")
fun hasAlpha(path: String): Boolean {
    val file = File(path)
    var result = false
    if (file.exists()) {
        ImageIcon(path).image?.apply {
            result = if (this is BufferedImage) {
                this.colorModel.hasAlpha()
            } else {
                PixelGrabber(this, 0, 0, 1, 1, false).apply {
                    this.grabPixels()
                }.colorModel?.hasAlpha() ?: false
            }
        }
    }

    return result
}

fun hasAlpha2(path: String): Boolean {
    val img = ImageIO.read(File(path))
    return img?.colorModel?.hasAlpha() ?: false
}