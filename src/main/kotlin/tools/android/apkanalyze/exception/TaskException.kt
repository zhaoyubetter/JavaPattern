package tools.android.apkanalyze.exception

import java.lang.RuntimeException

class TaskExecuteException : RuntimeException {
    constructor() : super()
    constructor(msg: String) : super(msg)
}

class TaskInitException(msg: String) : RuntimeException(msg) {
}