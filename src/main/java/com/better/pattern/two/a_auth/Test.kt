package com.better.pattern.two.a_auth

import org.junit.Test

class Test {
    @Test
    fun test1() {
        // 1. 客户端生成 AuthToken
        val baseUrl = "http://www.sonar.vip"
        val password = "222"
        val appId = "2"
        val ts = System.currentTimeMillis() - 500000

        val clientToken = AuthToken.generate(baseUrl = baseUrl, appId = appId, password = password, ts = ts)

        // 2. 客户端请求请求url
        val requestUrl = "$baseUrl?appId=$appId&ts=$ts&token=${clientToken.token}"

        // 3.服务端解析url
        val apiAuthencator = DefaultApiAuthencator()
        apiAuthencator.auth(requestUrl)
    }
}