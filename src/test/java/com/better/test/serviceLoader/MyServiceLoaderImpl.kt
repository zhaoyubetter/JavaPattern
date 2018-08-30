package com.better.test.serviceLoader

class MyServiceLoaderImpl : IMyServiceLoader {
    override fun sayHello(): String {
        return "Hello"
    }

    override fun getName(): String {
        return "better from impl"
    }
}