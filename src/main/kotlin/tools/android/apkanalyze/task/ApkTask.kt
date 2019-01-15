package tools.android.apkanalyze.task

import tools.android.apkanalyze.exception.TaskExecuteException
import tools.android.apkanalyze.result.TaskResult
import java.util.concurrent.Callable
import tools.android.apkanalyze.job.JobConfig


/**
 * 抽象任务
 */
abstract class ApkTask(taskType: Int, config: JobConfig, params: Map<String, String>) : Callable<TaskResult> {

    protected val taskType = taskType
    protected val config: JobConfig = config
    protected val params: Map<String, String> = params
    protected var progressListeners: List<ApkTaskProgressListener>? = null

    /**
     * 初始化
     */
    abstract fun init()


    @Throws(TaskExecuteException::class)
    abstract override fun call(): TaskResult

    interface ApkTaskProgressListener {
        fun getProgress(progress: Int, message: String)
    }
}