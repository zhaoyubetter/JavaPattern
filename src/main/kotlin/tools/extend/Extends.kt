package tools.extend

import java.io.File
import java.nio.file.Files


// About File
fun File.isLegal() = this.exists() && this.isFile && this.length() > 0 && this.canRead()

fun File.deleteDir() {
    val contents = this.listFiles()
    if (contents != null) {
        for (f in contents) {
            if (!Files.isSymbolicLink(f.toPath())) {
                f.deleteDir()
            }
        }
    }
    this.delete()
}

fun String.isNotNullOrBlank() = !this.isNullOrBlank()

fun <T> T.isNotNull() = this != null