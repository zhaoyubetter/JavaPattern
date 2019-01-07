package com.android.dexdeps.extension

import com.android.dexdeps.MethodRef
import com.android.dexdeps.Output

/**
 * Lorg/apache/http/conn/OperatedClientConnection =>
 * org.apache.http.conn.OperatedClientConnection
 */
fun MethodRef.getNormalClassName(): String {
    return if (!this.declClassName.isNullOrBlank()) {
        Output.descriptorToDot(declClassName).substringAfterLast("[]")
    } else ""
}