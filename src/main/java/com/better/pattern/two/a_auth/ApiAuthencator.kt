package com.better.pattern.two.a_auth

/**
 * 请求授权
 */
interface ApiAuthencator {
    fun auth(url: String)
    fun auth(apiRequest: ApiRequest)
}

class DefaultApiAuthencator : ApiAuthencator {

    private val credentialStorage = SqlCredentialStorage()

    override fun auth(url: String) {
        auth(ApiRequest.obtainApiRequest(url))
    }

    override fun auth(apiRequest: ApiRequest) {
        // 获取客户端传过来的参数
        val appId = apiRequest.appId
        val token = apiRequest.token
        val ts = apiRequest.ts
        val originalUrl = apiRequest.baseUrl

        // 1. 客户端的 token
        val authToken = AuthToken(token = token, ts = ts)
        if (authToken.isExpired()) {
            throw  RuntimeException("Token is expired.")
        }

        // 2. 生成服务端的token
        val password = credentialStorage.getPwdByAppId(appId)
        val serveToken = AuthToken.generate(appId = appId, ts = ts, baseUrl = originalUrl, password = password)

        // 3. 对比token
        if (!serveToken.isMatch(authToken)) {
            throw  RuntimeException("Token verfication failed.")
        }

        println("ok!")
    }
}


