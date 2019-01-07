package tools.android.apkanalyze

import java.io.File
import java.io.RandomAccessFile
import java.lang.IllegalArgumentException
import com.android.dexdeps.DexData
import com.android.dexdeps.extension.getNormalClassName


/**
 * 参考微信
 * 统计方法数
 * 统计出各个Dex中的方法数，并按照类名或者包名来分组输出结果
 **/
class MethodCountTask(unzipPath: String) {

    private val dexFileNameList = mutableListOf<String>()
    private val dexFileList = mutableListOf<RandomAccessFile>()
    private val inputFile = File(unzipPath)

    init {
        if (!inputFile.exists()) {
            throw  IllegalArgumentException(this.javaClass.simpleName + "---APK-UNZIP-PATH '" + unzipPath + "' is not exist!")
        }
        if (!inputFile.isDirectory) {
            throw  IllegalArgumentException(this.javaClass.simpleName + "---APK-UNZIP-PATH '" + unzipPath + "' is not directory!")
        }

        // all dex files
        inputFile.listFiles { it -> it.name.endsWith(ApkConstants.DEX_FILE_SUFFIX) }.forEach {
            dexFileNameList.add(it.name)
            dexFileList.add(RandomAccessFile(it, "rw"))
        }
    }

    fun getResult() {
        dexFileList.forEach {
            analysis(it)
        }
    }

    private inline fun analysis(dexFile: RandomAccessFile) {
        DexData(dexFile).apply {
            load()
            this.methodRefs.forEach {
                val className = it.getNormalClassName()   // original name

            }
        }
    }

}