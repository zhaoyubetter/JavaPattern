package com.better.pattern.two.a_auth

/**
 * 从存储中取出 AppID 和对应的密码
 */
interface CredentialStorage {
    fun getPwdByAppId(appId: String): String
}

class SqlCredentialStorage : CredentialStorage {

    private val map = mapOf("1" to "111", "2" to "222", "3" to "333")

    override fun getPwdByAppId(appId: String): String {
        return map.get(appId) ?: ""
    }
}