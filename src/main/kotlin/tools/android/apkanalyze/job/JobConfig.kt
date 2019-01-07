package tools.android.apkanalyze.job

import com.google.gson.JsonArray

/**
 * 来自微信
 * 用来保存 apk 分解后的文件信息
 * 与配置信息
 *
 */

/**
 * @param
 */
class JobConfig() {
    var inputDir: String? = null
    var apkPath: String? = null
    var unzipPath: String? = null
    var outputPath: String? = null
    var mappingFilePath: String? = null
    var resMappingFilePath: String? = null
    var outputConfig: JsonArray? = null

    var outputFormatList: List<String>? = null
    /**源代码混淆对应表*/
    var proguardClassMap: Map<String, String>? = null
    var resguardMap: Map<String, String>? = null
    /**
     * 解压后文件大小
     */
    var entrySizeMap: Map<String, Pair<Long, Long>>? = null
    /**
     * 文件名混淆对应表
     */
    var entryNameMap: Map<String, String>? = null
}