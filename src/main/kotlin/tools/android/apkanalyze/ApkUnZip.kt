package tools.android.apkanalyze

import com.google.gson.GsonBuilder
import tools.extend.deleteDir
import tools.extend.isLegal
import tools.log.DefaultLog
import tools.log.ILog
import java.io.File
import java.lang.IllegalArgumentException
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

fun main(args: Array<String>) {
    val apkPath = "/Users/zhaoyu1/apkAnalyze/Manto_jr-release12.28.apk"
    val outputPath = "/Users/zhaoyu1/apkAnalyze/unzip"

//    ApkUnZip(apkPath, outputPath).unzip()
    val json = ManifestParser(File("/Users/zhaoyu1/apkAnalyze/unzip/AndroidManifest.xml")).parse()
    println(GsonBuilder().setPrettyPrinting().create().toJson(json))
}


class ApkUnZip(inputFilePath: String, val outputPath: String) {

    private val inputFile: File
    private val outputFile: File
    private val log: ILog = DefaultLog()

    init {
        inputFile = File(inputFilePath).apply {
            if (!isLegal()) {
                throw IllegalArgumentException("File [${this.path}] is not valid.")
            }
        }
        outputFile = File(outputPath).apply {
            if (this.isDirectory && this.exists()) {
                this.deleteDir()
            } else if (this.isFile) {
                throw IllegalArgumentException("$outputPath must be a directory.")
            }
        }
    }

    fun unzip() {
        ZipFile(inputFile).apply {
            val entries = this.entries();
            while (entries.hasMoreElements()) {
                entries.nextElement()?.let {
                    writeEntry(zipFile@ this, it)
                }
            }
        }
    }

    private inline fun writeEntry(zipFile: ZipFile, zipEntry: ZipEntry) {
        val index = zipEntry.name.lastIndexOf("/")
        val dirName = zipEntry.name.substring(0, index + 1)
        var fileName: String
        var dir: File

        if (index >= 0) {
            fileName = zipEntry.name.substring(index)
            dir = File(outputPath, dirName)
        } else {
            fileName = zipEntry.name
            dir = File(outputPath)
        }

//        println("${zipEntry.name}, size1: ${zipEntry.compressedSize}, size2: ${zipEntry.size}")

        if (!dir.exists() && !dir.mkdirs()) {
            log.error("can't mkdirs")
        }

        File(dir, fileName).apply {
            if (this.createNewFile()) {
                this.writeBytes(zipFile.getInputStream(zipEntry).readBytes())
            } else {
                log.error("can't createNewFile()")
            }
        }
    }

}