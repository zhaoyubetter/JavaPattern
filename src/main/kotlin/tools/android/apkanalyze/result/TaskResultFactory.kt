package tools.android.apkanalyze.result

import tools.android.apkanalyze.job.JobConfig


class TaskResultFactory {

    companion object {

        val TASK_RESULT_TYPE_JSON = "json"

        fun <T : TaskResult> factory(taskType: Int, resultType: String, config: JobConfig): T? {
            var result: T? = null
            if (TASK_RESULT_TYPE_JSON == resultType) {
                result = TaskJsonResult(taskType) as T
            }
            return result
        }
    }
}