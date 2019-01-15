package tools.android.apkanalyze.task

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import tools.android.apkanalyze.ApkConstants
import tools.android.apkanalyze.exception.TaskExecuteException
import tools.android.apkanalyze.exception.TaskInitException
import tools.android.apkanalyze.job.JobConfig
import tools.android.apkanalyze.job.JobConstants
import tools.android.apkanalyze.result.TaskJsonResult
import tools.android.apkanalyze.result.TaskResult
import tools.android.apkanalyze.result.TaskResultFactory
import tools.android.apkanalyze.result.TaskResultFactory.Companion.TASK_RESULT_TYPE_JSON
import tools.android.apkanalyze.task.TaskFactory.Companion.TASK_TYPE_SHOW_FILE_SIZE
import tools.log.Log
import java.io.File
import tools.extend.isNotNullOrBlank
import java.util.*


/**
 * 显示文件并按大小排序，
 * 可选参数：
 *  @see JobConstants.PARAM_MIN_SIZE_IN_KB  文件大小
 *  @see JobConstants.PARAM_ORDER 排序
 *  @see JobConstants.PARAM_SUFFIX 文件后缀过滤
 */
class ShowFileSizeTask(config: JobConfig, params: HashMap<String, String>) :
        ApkTask(TASK_TYPE_SHOW_FILE_SIZE, config, params) {

    private val TAG = this.javaClass.simpleName
    private lateinit var inputFile: File
    private var downLimit: Long = 0L
    private var order = JobConstants.ORDER_DESC
    private var filterSuffix = setOf<String>()

    override fun init() {
        val inputPath = config.unzipPath
        if (config.unzipPath.isNullOrBlank()) {
            throw  TaskInitException("$TAG---APK-UNZIP-PATH can not be null!")
        }
        Log.d(TAG, "$inputPath")

        inputFile = File(inputPath).apply {
            if (!this.exists()) {
                throw  TaskInitException("$TAG---APK-UNZIP-PATH $inputPath is not exist!")
            }
            if (!this.isDirectory) {
                throw  TaskInitException("$TAG---APK-UNZIP-PATH $inputPath is not directory!")
            }
        }

        verifyParams()
    }

    private inline fun verifyParams() {
        // 最小
        if (params.containsKey(JobConstants.PARAM_MIN_SIZE_IN_KB)) {
            try {
                downLimit = params[JobConstants.PARAM_MIN_SIZE_IN_KB]?.toLong() ?: 0L
            } catch (e: NumberFormatException) {
                downLimit = 0L
                Log.e(TAG, "DOWN-LIMIT-SIZE '" + params[JobConstants.PARAM_MIN_SIZE_IN_KB] + "' is not number format!")
            }
        }

        // 排序
        if (params.containsKey(JobConstants.PARAM_ORDER)) {
            when {
                JobConstants.ORDER_ASC == params[JobConstants.PARAM_ORDER] -> order = JobConstants.ORDER_ASC
                JobConstants.ORDER_DESC == params[JobConstants.PARAM_ORDER] -> order = JobConstants.ORDER_DESC
                else -> Log.e(TAG, "ORDER-BY '" + params[JobConstants.PARAM_ORDER] + "' is not correct!")
            }
        }

        // 文件过滤
        params[JobConstants.PARAM_SUFFIX]?.let {
            if (it.isNotNullOrBlank()) {
                filterSuffix = it.split(",".toRegex()).dropLastWhile { suf -> suf.isEmpty() }.map { suf -> suf.trim() }.toMutableSet()
            }
        }
    }

    override fun call(): TaskResult {
        val taskResult = TaskResultFactory.factory<TaskJsonResult>(taskType, TASK_RESULT_TYPE_JSON, config)
        val startTime = System.currentTimeMillis()
        if (taskResult != null) {
            val jsonArray = config.entrySizeMap?.filter { it ->
                val size = it.value.first >= downLimit * ApkConstants.K1024        // 文件大小过滤
                val suf = if (filterSuffix.isNotEmpty()) {                         //
                    filterSuffix.contains(it.key.substringAfterLast("."))
                } else false
                size && suf
            }?.map { it -> Pair(it.key, it.value.first) }?.sortedWith(Comparator { o1, o2 ->
                if (order == JobConstants.ORDER_DESC) {
                    (o2.second - o1.second).toInt()

                } else {
                    (o1.second - o2.second).toInt()
                }
            })?.fold(JsonArray()) { acc, pair ->
                acc.apply {
                    add(JsonObject().apply {
                        this.addProperty("entry-name", pair.first)
                        this.addProperty("entry-size", pair.second)
                    })
                }
            } ?: JsonArray()

            taskResult.add("files", jsonArray)
            taskResult.setStartTime(startTime)
            taskResult.setEndTime(System.currentTimeMillis())
            return taskResult
        }

        throw  TaskExecuteException("$TAG  taskResult is null.")
    }

}