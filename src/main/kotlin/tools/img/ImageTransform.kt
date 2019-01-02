package tools.img

import tools.log.DefaultLog
import java.io.File
import java.util.*

/**
 * abstract class
 */
abstract class ImageTransform {

    protected val log = DefaultLog()

    /**
     * Transform image use specify path
     * @param path
     * @param suffixs suffix 扩展名
     * @param excludeSuffixs
     */
    fun transform(path: String, suffixs: Array<String>, excludeSuffixs: Array<String>? = null) {
        val list = getImageInfoList(path, suffixs, excludeSuffixs)
        if (!list.isEmpty()) {
            list.forEach { transformOne(it) }
        }
    }

    /**
     * Get all imageInfo
     * @param path
     * @param suffix suffix 扩展名
     * @param excludeSuffix
     */
    open fun getImageInfoList(path: String, suffixs: Array<String>, excludeSuffixs: Array<String>? = null): List<ImageInfo> {
        val dir = File(path)
        if (dir.exists()) {
            var list = dir.listFiles { file ->
                suffixs.any { file.name.endsWith(it) }
            }.toList()
            excludeSuffixs?.apply {
                list = list.filterNot { file ->
                    this.any { file.name.endsWith(it) }
                }
            }
            return list.map {
                ImageInfo(it.name, it.path, it.length(), ImageFormat.Unkown)
            }.sortedBy { it.name }
        }

        return Collections.emptyList()
    }

    abstract fun transformOne(imageInfo: ImageInfo)
}


