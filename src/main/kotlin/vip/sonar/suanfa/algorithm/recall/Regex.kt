package vip.sonar.suanfa.algorithm.recall

import org.junit.Test

class Regex {

    // 正则与其长度
    class Pattern2(val pattern: String, val pLen: Int) {
        var matched = false

        // 文本串与长度
        fun match(text: String, tLen: Int): Boolean {
            matched = false
            rmatch(0, 0, text, tLen)
            return matched
        }

        /**
         * @param ti 文本位
         * @param pj 正则位
         * @param text 匹配文本数组
         * @param tLen 长度
         */
        private fun rmatch(ti: Int, pj: Int, text: String, tLen: Int) {
            if (matched) return
            if (pj == pLen) {                       // 正则表达式到尾了
                if (ti == tLen) matched = true      // 文本到尾了
                return
            }

            // “*”有多种匹配方案，可以匹配任意个文本串中的字符，
            // 我们就先随意的选择一种匹配方案，然后继续考察剩下的字符。如果中途发现无法继续匹配下去了，
            // 我们就回到这个岔路口，重新选择一种匹配方案，然后再继续匹配剩下的字符
            if (pattern[pj] == '*') {  // * 匹配任意个字符
                for (k in (0..tLen - ti)) {
                    // ti + k 表示下一个匹配字符， pj + 1 下一个正则字符
                    rmatch(ti + k, pj + 1, text, tLen)
                }
            } else if (pattern[pj] == '?') {    // ? 匹配 0 个或者 1 个字符
                rmatch(ti, pj + 1, text, tLen)          // 0 个字符
                rmatch(ti + 1, pj + 1, text, tLen)   // 1 个字符
            } else if (ti < tLen && pattern[pj] == text[ti]) { // 纯字符匹配才行
                rmatch(ti + 1, pj + 1, text, tLen)
            }
        }
    }

    @Test
    fun test() {
        var pattern = Pattern2("*", "*".length)
        pattern.match("abcd", "abcd".length)
        println(pattern.matched)
    }
}