package com.better.test.serviceLoader

import java.util.*

fun main(args: Array<String>) {
    val serviceLoader = ServiceLoader.load(IMyServiceLoader::class.java)
    for (face in serviceLoader) {
        println(face.sayHello())
    }

}