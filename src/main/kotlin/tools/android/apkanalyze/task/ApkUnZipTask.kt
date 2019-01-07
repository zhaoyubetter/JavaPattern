package tools.android.apkanalyze.task

import com.google.gson.JsonObject
import tools.android.apkanalyze.exception.TaskInitException
import tools.android.apkanalyze.job.JobConfig
import tools.android.apkanalyze.result.TaskResult
import tools.android.apkanalyze.result.TaskResultFactory
import tools.android.apkanalyze.result.TaskResultFactory.Companion.TASK_RESULT_TYPE_JSON
import tools.android.apkanalyze.task.TaskFactory.Companion.TASK_TYPE_UNZIP
import tools.extend.deleteDir
import tools.extend.isLegal
import tools.extend.isNotNullOrBlank
import tools.log.Log
import java.io.File
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.HashMap
import java.io.FileReader
import java.io.BufferedReader
import com.google.gson.JsonArray
import tools.android.apkanalyze.exception.TaskExecuteException
import tools.android.apkanalyze.result.TaskJsonResult


/**
 * apk解压任务
 */
class ApkUnZipTask(config: JobConfig, params: Map<String, String>) : ApkTask(TASK_TYPE_UNZIP, config, params) {

    private val TAG = this.javaClass.simpleName

    /** apk 文件 */
    private lateinit var inputFile: File
    /** 输出 目录 */
    private lateinit var outputFile: File
    private var mappingTxt: File? = null
    private var resMappingTxt: File? = null
    /** 源代码混淆对应关系表 */
    private var proguardClassMap: HashMap<String, String>? = null
    private var resguardMap: HashMap<String, String>? = null
    private var resDirMap: HashMap<String, String>? = null
    // 文件名对应关系表
    private var entryNameMap: HashMap<String, String>
    // 解压后文件大小
    private lateinit var entrySizeMap: HashMap<String, Pair<Long, Long>>

    init {
        proguardClassMap = HashMap()
        resguardMap = HashMap()
        resDirMap = HashMap()
        entryNameMap = HashMap()
        entrySizeMap = HashMap()
    }

    override fun init() {
        inputFile = File(config.apkPath).apply {
            if (!isLegal()) {
                throw IllegalArgumentException("File [${this.path}] is not valid.")
            }
        }
        outputFile = File(config.outputPath).apply {
            if (this.isFile) {
                throw IllegalArgumentException("${config.outputPath} must be a directory.")
            }
        }

        Log.d(TAG, "outputPath: ${config.outputPath}")

        // 1. mappingTxt
        if (config.mappingFilePath?.isNotNullOrBlank() == true) {
            mappingTxt = File(config.mappingFilePath).apply {
                if (!isLegal()) {
                    throw TaskInitException(TAG + "---mapping file " + config.mappingFilePath + " is not legal!")
                }
            }
        } else {
            Log.d(TAG, "mappingFilePath is null.")
        }

        // 2.resMapping
        if (config.resMappingFilePath?.isNotNullOrBlank() == true) {
            resMappingTxt = File(config.resMappingFilePath).apply {
                if (!isLegal()) {
                    throw TaskInitException(TAG + "---resguard mapping file " + config.resMappingFilePath + " is not legal!")
                }
            }
        } else {
            Log.d(TAG, "resMappingFilePath is null.s")
        }
    }

    @Throws(TaskExecuteException::class)
    override fun call(): TaskResult {
        try {
            val zipFile = ZipFile(inputFile)
            // 移除旧的输出目录
            if (outputFile.isDirectory && outputFile.exists()) {
                Log.d(TAG, "%s exists, delete it.", outputFile.getAbsolutePath());
                outputFile.deleteDir()
            }

            // 任务结果
            val taskResult: TaskJsonResult = TaskResultFactory.factory(type, TASK_RESULT_TYPE_JSON, config)
                    ?: throw TaskExecuteException("$TAG  taskResult is null.")

            if (!outputFile.mkdir()) {
                throw  TaskExecuteException(TAG + "---Create directory '" + outputFile.absolutePath + "' failed!")
            }


            val startTime = System.currentTimeMillis()

            // apk 大小
            taskResult.add("total-size", inputFile.length())

            // 1.处理mapping
            readMappingTxtFile()
            config.proguardClassMap = proguardClassMap
            // 2.处理ResMapping
            readResMappingTxtFile()
            config.resguardMap = resguardMap

            val entries = zipFile.entries()
            val jsonArray = JsonArray()
            while (entries.hasMoreElements()) {
                entries.nextElement()?.let {
                    val outEntryName = writeEntry(zipFile, it)
                    if (outEntryName?.isNotNullOrBlank() == true) {
                        val fileItem = JsonObject()
                        fileItem.addProperty("entry-name", outEntryName)
                        fileItem.addProperty("entry-size", it.compressedSize)
                        jsonArray.add(fileItem)
                        entrySizeMap.put(outEntryName!!, Pair(it.size, it.compressedSize))
                        entryNameMap.put(it.name, outEntryName)
                    }
                }
            }

            config.entrySizeMap = entrySizeMap
            config.entryNameMap = entryNameMap
            taskResult.add("entries", jsonArray)
            taskResult.setStartTime(startTime)
            taskResult.setEndTime(System.currentTimeMillis())

            return taskResult
        } catch (e: Exception) {
            throw TaskExecuteException(e.message ?: "")
        }
        throw  TaskExecuteException("$TAG  taskResult is null.")
    }

    private inline fun readResMappingTxtFile() {
        if (resMappingTxt != null) {
            val bufferedReader = BufferedReader(FileReader(resMappingTxt))
            try {
                var line: String? = bufferedReader.readLine()
                var readResStart = false
                var readPathStart = false
                while (line != null) {
                    if (line.trim { it <= ' ' } == "res path mapping:") {
                        readPathStart = true
                    } else if (line.trim { it <= ' ' } == "res id mapping:") {
                        readResStart = true
                        readPathStart = false
                    } else if (readPathStart) {
                        val columns = line.split("->".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (columns.size == 2) {
                            val before = columns[0].trim { it <= ' ' }
                            val after = columns[1].trim { it <= ' ' }
                            if (before.isNotNullOrBlank() && after.isNotNullOrBlank()) {
                                resDirMap?.put(after, before)
                            }
                        }
                    } else if (readResStart) {
                        val columns = line.split("->".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (columns.size == 2) {
                            val before = parseResourceNameFromResguard(columns[0].trim { it <= ' ' })
                            val after = parseResourceNameFromResguard(columns[1].trim { it <= ' ' })
                            if (before.isNotNullOrBlank() && after.isNotNullOrBlank()) {
                                resguardMap?.put(after, before)
                            }
                        }
                    }
                    line = bufferedReader.readLine()
                }
            } finally {
                bufferedReader.close()
            }
        }
    }

    private inline fun parseResourceNameFromResguard(resName: String): String {
        if (resName.isNotNullOrBlank()) {
            val index = resName.indexOf('R')
            if (index >= 0) {
                return resName.substring(index)
            }
        }
        return ""
    }

    private inline fun readMappingTxtFile() {
        if (mappingTxt != null) {
            val bufferedReader = BufferedReader(FileReader(mappingTxt))
            var line: String? = bufferedReader.readLine()
            var beforeClass = ""
            var afterClass = ""
            try {
                while (line != null) {
                    if (!line.startsWith(" ")) {
                        val pair = line.split("->".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (pair.size == 2) {
                            beforeClass = pair[0].trim { it <= ' ' }  // 混淆前
                            afterClass = pair[1].trim { it <= ' ' }   // 混淆后
                            afterClass = afterClass.substring(0, afterClass.length - 1)
                            if (beforeClass.isNotNullOrBlank() && afterClass.isNotNullOrBlank()) {
                                proguardClassMap?.put(afterClass, beforeClass)
                            }
                        }
                    }
                    line = bufferedReader.readLine()
                }
            } finally {
                bufferedReader.close()
            }
        }
    }


    private inline fun writeEntry(zipFile: ZipFile, zipEntry: ZipEntry): String? {
        val index = zipEntry.name.lastIndexOf("/")
        val dirName = zipEntry.name.substring(0, index + 1)
        var fileName: String
        var dir: File
        var outEntryName: String? = null

        if (index >= 0) {
            fileName = zipEntry.name.substring(index)
            dir = File(outputFile, dirName)
            if (!dir.exists() && !dir.mkdirs()) {
                Log.e(TAG, "%s mkdirs failed!", dir.absolutePath);
                return null
            }
            // 反混淆名称
            if (fileName.isNotNullOrBlank()) {
                outEntryName = reverseResguard(dirName, fileName)
                if (outEntryName.isNullOrEmpty()) {
                    outEntryName = zipEntry.name
                }
            }
        } else {  // 根目录下，直接获取文件名
            fileName = zipEntry.name
            dir = outputFile
            outEntryName = zipEntry.name
        }

        if (!dir.exists() && !dir.mkdirs()) {
            Log.e(TAG, "can't mkdirs")
            return null
        }

        File(dir, fileName).apply {
            if (this.createNewFile()) {
                this.writeBytes(zipFile.getInputStream(zipEntry).readBytes())
            } else {
                Log.e(TAG, "can't createNewFile()")
                return null
            }
        }

        return outEntryName
    }

    // 反混淆
    private inline fun reverseResguard(dirName: String, filename: String): String {
        var filename = filename
        var outEntryName = ""
        if (resDirMap?.containsKey(dirName) == true) {
            val newDirName = resDirMap?.get(dirName)
            val resource = parseResourceNameFromPath(newDirName, filename)
            val suffixIndex = filename.indexOf('.')
            var suffix = ""
            if (suffixIndex >= 0) {
                suffix = filename.substring(suffixIndex)
            }
            if (resguardMap?.containsKey(resource) == true) {
                val lastIndex = resguardMap?.get(resource)?.lastIndexOf('.') ?: -1
                if (lastIndex >= 0) {
                    filename = resguardMap?.get(resource)?.substring(lastIndex + 1) + suffix
                }
            }
            outEntryName = "$newDirName/$filename"
        }
        return outEntryName
    }

    private inline fun parseResourceNameFromPath(dir: String?, filename: String?): String {
        var filename = filename
        if (dir.isNullOrEmpty() || filename.isNullOrEmpty()) {
            return ""
        }

        var type = dir?.substring(dir.indexOf('/') + 1)
        var index = type?.indexOf('-') ?: -1
        if (index >= 0) {
            type = type?.substring(0, index)
        }
        index = filename?.indexOf('.') ?: -1
        if (index >= 0) {
            filename = filename?.substring(0, index)
        }
        return "R.$type.$filename"
    }

}