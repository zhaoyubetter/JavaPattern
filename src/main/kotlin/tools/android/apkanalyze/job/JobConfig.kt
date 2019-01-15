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
class JobConfig {
    var inputDir: String? = null
    var apkPath: String? = null
    var unzipPath: String? = null
    var outputPath: String? = null
    var mappingFilePath: String? = null
    /** 资源文件混淆 mapping 文件 微信提供 */
    var resMappingFilePath: String? = null
    var outputConfig: JsonArray? = null

    var outputFormatList: List<String>? = null
    /**源代码混淆对应表*/
    var proguardClassMap: Map<String, String>? = null
    /** 资源混淆对应表 */
    var resguardMap: Map<String, String>? = null
    /**
     * 解压后文件大小
     * 文件名 <原大小，解压后大小>
     */
    var entrySizeMap: Map<String, Pair<Long, Long>>? = null
    /**
     * 文件名混淆对应表
     */
    var entryNameMap: Map<String, String>? = null
}