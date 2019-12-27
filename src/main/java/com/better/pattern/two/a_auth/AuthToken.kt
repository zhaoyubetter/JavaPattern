package com.better.pattern.two.a_auth

import sun.security.provider.MD5
import java.lang.StringBuilder
import java.net.URI
import java.security.MessageDigest
import java.util.*
import java.util.Base64.getEncoder
import com.sun.deploy.util.Base64Wrapper.encodeToString
import java.nio.charset.Charset


/**
 * 把 URL、AppID、密码、时间戳拼接为一个字符串；
 * 对字符串通过加密算法加密生成 token；
 * 根据时间戳判断 token 是否过期失效；
 * 验证两个 token 是否匹配。
 *
 * token : 传入的 token
 * createTime: token 生成时间
 * expiredTime：有效期
 */
class AuthToken(
        val token: String,
        val ts: Long,
        val expiredTime: Long = 3 * 60 * 1000) {


    companion object {
        /**
         * 生成 token
         */
        fun generate(baseUrl: String, appId: String, password: String,
                     ts: Long): AuthToken {
            val sb = StringBuilder(baseUrl)
            sb.append("?")
            sb.append("appId=$appId&")
            sb.append("password=$password&")
            sb.append("ts=$ts")
            val token = MessageDigestUtils.md5(sb.toString())
            return AuthToken(token, ts)
        }
    }

    fun isExpired(): Boolean {
        return System.currentTimeMillis() - ts > expiredTime
    }

    fun isMatch(other: AuthToken): Boolean {
        return this.token == other.token
    }
}