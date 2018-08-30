package com.better.test.serviceLoader

class MyServiceLoaderImpl2 : IMyServiceLoader {
    override fun sayHello(): String {
        return "Hello Kotlin"
    }

    override fun getName(): String {
        return "better from impl2"
    }
}