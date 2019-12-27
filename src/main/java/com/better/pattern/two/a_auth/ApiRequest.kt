package com.better.pattern.two.a_auth

import java.net.URL

/**
 * 将 token、AppID、时间戳拼接到 URL 中，形成新的 URL；
 * 解析 URL，得到 token、AppID、时间戳等信息。
 */
class ApiRequest(val baseUrl: String, val token: String, val appId: String, val ts: Long) {

    companion object {
        /**
         * 将 url 中的内容解析出来
         * http://xxxxx?appId=222&token=tokenXXXX
         */
        fun obtainApiRequest(pUrl: String): ApiRequest {
            val url = URL(pUrl)
            // 解析参数
            val params = HashMap<String, String>()
            url.query.split("&").forEach { it ->
                val list = it.split("=")
                params[list[0]] = list[1]
            }
            val baseUrl = pUrl.substringBefore("?")
            val appId = params["appId"]!!
            val token = params["token"]!!
            val ts = params["ts"]?.toLong() ?: 0L
            return ApiRequest(baseUrl, token, appId, ts)
        }
    }
}