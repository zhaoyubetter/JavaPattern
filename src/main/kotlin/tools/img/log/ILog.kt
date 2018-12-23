package tools.img.log

import jline.internal.Log

interface ILog {
    fun info(msg: String)
    fun debug(msg: String)
    fun warn(msg: String, e: Throwable? = null)
    fun error(msg: String? = null, e: Throwable? = null)
    fun error(e: Throwable)
}

class DefaultLog : ILog {
    override fun error(e: Throwable) {
        Log.error(e)
    }

    override fun info(msg: String) {
        Log.info(msg)
    }

    override fun debug(msg: String) {
        Log.debug(msg)
    }

    override fun error(msg: String?, e: Throwable?) {
        Log.error(msg, e)
    }

    override fun warn(msg: String, e: Throwable?) {
        Log.warn(msg, e)
    }
}