package tools.log

import jline.internal.Log
import java.io.PrintWriter
import java.io.StringWriter


/**
 * use weixin log
 */
class Log {
    companion object {
        private val logImp = object : ILog2 {
            override fun v(tag: String, msg: String, vararg obj: Any) {
                val log = if (obj == null) msg else String.format(msg, *obj)
                println(String.format("[VERBOSE][%s]%s", tag, log))
            }

            override fun i(tag: String, msg: String, vararg obj: Any) {
                val log = if (obj == null) msg else String.format(msg, *obj)
                println(String.format("[INFO][%s]%s", tag, log))
            }

            override fun d(tag: String, msg: String, vararg obj: Any) {
                val log = if (obj == null) msg else String.format(msg, *obj)
                println(String.format("[DEBUG][%s]%s", tag, log))
            }

            override fun w(tag: String, msg: String, vararg obj: Any) {
                val log = if (obj == null) msg else String.format(msg, *obj)
                println(String.format("[WARN][%s]%s", tag, log))
            }

            override fun e(tag: String, msg: String, vararg obj: Any) {
                val log = if (obj == null) msg else String.format(msg, *obj)
                println(String.format("[ERROR][%s]%s", tag, log))
            }

            override fun printErrStackTrace(tag: String, tr: Throwable, format: String, vararg obj: Any) {
                var log: String? = if (obj == null) format else String.format(format, *obj)
                if (log == null) {
                    log = ""
                }
                val sw = StringWriter()
                val pw = PrintWriter(sw)
                tr.printStackTrace(pw)
                log += "  " + sw.toString()
                println(String.format("[ERROR][%s]%s", tag, log))
            }
        }

        fun v(tag: String, msg: String, vararg obj: Any) {
            logImp.v(tag, msg, obj)
        }

        fun e(tag: String, msg: String, vararg obj: Any) {
            logImp.e(tag, msg, obj)
        }

        fun w(tag: String, msg: String, vararg obj: Any) {
            logImp.w(tag, msg, obj)
        }
    }
}

interface ILog2 {

    fun v(tag: String, msg: String, vararg obj: Any)

    fun i(tag: String, msg: String, vararg obj: Any)

    fun w(tag: String, msg: String, vararg obj: Any)

    fun d(tag: String, msg: String, vararg obj: Any)

    fun e(tag: String, msg: String, vararg obj: Any)

    fun printErrStackTrace(tag: String, tr: Throwable, format: String, vararg obj: Any)

}


////////////////////
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
        Log.error(msg ?: "", e ?: "")
    }

    override fun warn(msg: String, e: Throwable?) {
        Log.warn(msg ?: "", e ?: "")
    }
}