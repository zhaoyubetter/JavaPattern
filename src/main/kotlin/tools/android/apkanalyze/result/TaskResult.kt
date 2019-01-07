package tools.android.apkanalyze.result

import com.google.gson.JsonObject
import tools.android.apkanalyze.task.TaskFactory
import java.util.Calendar
import java.text.SimpleDateFormat
import com.google.gson.JsonElement


/**
 * 任务结果
 */
abstract class TaskResult(val taskType: Int) {

    private var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")

    protected var startTime: String? = null
    protected var endTime: String? = null


    open fun setStartTime(startTime: Long) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = startTime
        this.startTime = dateFormat.format(calendar.time)
    }

    open fun setEndTime(endTime: Long) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = endTime
        this.endTime = dateFormat.format(calendar.time)
    }

    open abstract fun getResult(): Any
}

/**
 * json形式结果
 */
class TaskJsonResult(taskType: Int) : TaskResult(taskType) {

    private val jsonObject = JsonObject()

    init {
        jsonObject.addProperty("taskType", taskType)
        jsonObject.addProperty("taskDescription", TaskFactory.TaskDescription[taskType])
    }

    fun add(name: String, value: String) {
        jsonObject.addProperty(name, value)
    }

    fun add(name: String, value: Boolean) {
        jsonObject.addProperty(name, value)
    }

    fun add(name: String, value: Number) {
        jsonObject.addProperty(name, value)
    }

    fun add(name: String, jsonElement: JsonElement) {
        jsonObject.add(name, jsonElement)
    }

    fun format(jsonObject: JsonObject) {
    }

    override fun setStartTime(startTime: Long) {
        super.setStartTime(startTime)
        jsonObject.addProperty("start-time", this.startTime)
    }

    override fun setEndTime(endTime: Long) {
        super.setEndTime(endTime)
        jsonObject.addProperty("end-time", this.endTime)
    }

    override fun toString(): String {
        return jsonObject.toString()
    }

    override fun getResult(): JsonObject {
        return jsonObject
    }
}