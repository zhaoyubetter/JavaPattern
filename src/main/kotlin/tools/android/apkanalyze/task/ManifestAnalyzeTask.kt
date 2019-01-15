package tools.android.apkanalyze.task

import tools.android.apkanalyze.ApkConstants.ARSC_FILE_NAME
import tools.android.apkanalyze.ApkConstants.MANIFEST_FILE_NAME
import tools.android.apkanalyze.exception.TaskExecuteException
import tools.android.apkanalyze.exception.TaskInitException
import tools.android.apkanalyze.job.JobConfig
import tools.android.apkanalyze.result.TaskJsonResult
import tools.android.apkanalyze.result.TaskResult
import tools.android.apkanalyze.result.TaskResultFactory
import tools.android.apkanalyze.task.TaskFactory.Companion.TASK_TYPE_MANIFEST
import tools.extend.isLegal
import tools.log.Log
import java.io.File

/**
 * 清单文件分析
 */
class ManifestAnalyzeTask(config: JobConfig, params: Map<String, String>)
    : ApkTask(TASK_TYPE_MANIFEST, config, params) {

    private val TAG = "ManifestAnalyzeTask"

    private val ROOTTAG = "manifest"
    // 清单文件
    private lateinit var manifestFile: File
    // 对应 resources.arsc
    private lateinit var arscFile: File


    override fun init() {
        val inputFile = config.unzipPath
        if (config.unzipPath.isNullOrBlank()) {
            throw  TaskInitException("$TAG---APK-UNZIP-PATH can not be null!")
        }

        Log.d(TAG, "$inputFile")
        manifestFile = File(inputFile, MANIFEST_FILE_NAME)
        if (!manifestFile.isLegal()) {
            throw  TaskInitException("$TAG---Manifest file $inputFile${File.separator}$MANIFEST_FILE_NAME is not legal!")
        }

        arscFile = File(inputFile, ARSC_FILE_NAME)
    }

    @Throws(TaskExecuteException::class)
    override fun call(): TaskResult {
        val manifestParser = if (arscFile.isLegal()) ManifestParser(manifestFile)
        else ManifestParser(manifestFile, arscFile)

        val startTime = System.currentTimeMillis()
        val jsonObject = manifestParser.parse()
        val taskResult = TaskResultFactory.factory<TaskJsonResult>(taskType,
                TaskResultFactory.TASK_RESULT_TYPE_JSON, config)

        if (taskResult != null) {
            taskResult.add("manifest", jsonObject)
            taskResult.setStartTime(startTime)
            taskResult.setEndTime(System.currentTimeMillis())
            return taskResult
        }

        throw  TaskExecuteException("$TAG  taskResult is null.")
    }

}