package tools.android.apkanalyze

import tools.android.apkanalyze.job.JobConfig
import tools.android.apkanalyze.job.JobConstants
import tools.android.apkanalyze.task.ManifestAnalyzeTask
import tools.android.apkanalyze.task.ShowFileSizeTask
import tools.android.apkanalyze.task.UnZipTask

fun main(args: Array<String>) {
//    val apkPath = "/Users/zhaoyu1/apkAnalyze/Manto_jr-release12.28.apk"
//    val outputPath = "/Users/zhaoyu1/apkAnalyze/unzip"
//
////    UnZipTask(apkPath, outputPath).unzip()
//
//    /* 清单文件解析*/
//    val json = ManifestParser(File("/Users/zhaoyu1/apkAnalyze/unzip/AndroidManifest.xml")).parse()
//    println(GsonBuilder().setPrettyPrinting().generate().toJson(json))


    /* dex文件解析，方法数量
     */
    // MethodCountTask(outputPath).getResult()

    val config = JobConfig().apply {
        apkPath = "/Users/zhaoyu1/apkAnalyze//br_5.3.0_.apk"
        unzipPath = "/Users/zhaoyu1/apkAnalyze//output"
        mappingFilePath = "/Users/zhaoyu1/apkAnalyze//mapping.txt"
    }


    /* === 1. 解压任务 */
    UnZipTask(config, HashMap()).apply {
        init()
        val result = call()
        println(result.taskType)
    }

    /* === 2. 清单文件分析
    ManifestAnalyzeTask(config, HashMap()).apply {
        init()
        val result = call()
        println(result.taskType)
    }*/

    /* === 3. 文件大小排序
    ShowFileSizeTask(config, HashMap<String, String>().apply {
        put(JobConstants.PARAM_MIN_SIZE_IN_KB, "10")
        put(JobConstants.PARAM_SUFFIX, "png")
        put(JobConstants.ORDER_DESC, JobConstants.ORDER_DESC)
    }).apply {
        init()
        val result = call()
        println(result.taskType)
    }*/


}