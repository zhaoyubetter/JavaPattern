package tools.android.apkanalyze.task


class TaskFactory {
    companion object {

        const val TASK_TYPE_UNZIP = 1

        // 任务描述
        val TaskDescription = arrayOf(
                "Useless Task for default task type.",
                "Unzip the apk file to dest path.",
                "Read package info from the AndroidManifest.xml.",
                "Show files whose size exceed limit size in order.",
                "Count methods in dex file, output results group by class name or package name.",
                "Check if the apk handled by resguard.",
                "Find out the non-alpha png-format files whose size exceed limit size in desc order.",
                "Check if there are more than one library dir in the 'lib'.",
                "Show uncompressed file types.",
                "Count the R class.",
                "Find out the duplicated files.",
                "Check if there are more than one shared library statically linked the STL.",
                "Find out the unused resources.",
                "Find out the unused assets.",
                "Find out the unstripped shared library files.",
                "Count classes in dex file, output results group by package name.")
    }


}