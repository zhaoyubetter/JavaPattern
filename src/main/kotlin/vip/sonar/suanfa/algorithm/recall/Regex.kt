package vip.sonar.suanfa.algorithm.recall

import org.junit.Test

class Regex {

    // 正则与其长度
    class Pattern2(val pattern: Array<Char>, val pLen: Int) {
        var matched = false

        // 文本串与长度
        fun match(text: Array<Char>, tLen: Int): Boolean {
            matched = false
            rmatch(0, 0, text, tLen)
            return matched
        }

        private fun rmatch(ti: Int, pj: Int, text: Array<Char>, tLen: Int) {
            if (matched) return
            if (pj == pLen) {    // 正则表达式到尾了
                if (ti == tLen) matched = true  // 文本到尾了
                return
            }

            if (pattern[pj] == '*') {  // * 匹配任意个字符
                for (k in (0..tLen - ti)) {
                    rmatch(ti + k, pj + 1, text, tLen)
                }
            } else if (pattern[pj] == '?') {    // ? 匹配 0 个或者 1 个字符
                rmatch(ti, pj + 1, text, tLen)
                rmatch(ti + 1, pj + 1, text, tLen)
            } else if (ti < tLen && pattern[pj] == text[ti]) { // 纯字符匹配才行
                rmatch(ti + 1, pj + 1, text, tLen)
            }
        }
    }
}