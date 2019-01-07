package tools.android.apkanalyze

import com.google.gson.GsonBuilder
import tools.android.apkanalyze.job.JobConfig
import tools.android.apkanalyze.task.ApkUnZipTask
import java.io.File

fun main(args: Array<String>) {
//    val apkPath = "/Users/zhaoyu1/apkAnalyze/Manto_jr-release12.28.apk"
//    val outputPath = "/Users/zhaoyu1/apkAnalyze/unzip"
//
////    ApkUnZipTask(apkPath, outputPath).unzip()
//
//    /* 清单文件解析*/
//    val json = ManifestParser(File("/Users/zhaoyu1/apkAnalyze/unzip/AndroidManifest.xml")).parse()
//    println(GsonBuilder().setPrettyPrinting().create().toJson(json))


    /* dex文件解析，方法数量
     */
    // MethodCountTask(outputPath).getResult()

    val config = JobConfig().apply {
        apkPath = "/Users/zhaoyu1/apkAnalyze//.3.0_.apk"
        outputPath = "/Users/zhaoyu1/apkAnalyze//output"
        mappingFilePath = "/Users/zhaoyu1/apkAnalyze//mapping.txt"
    }

    ApkUnZipTask(config, HashMap()).apply {
        init()
        val result = call()
        println(result.taskType)
    }

}