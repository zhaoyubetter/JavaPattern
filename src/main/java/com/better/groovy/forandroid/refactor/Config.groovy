package com.better.groovy.forandroid.refactor

class Config {
    /**
     * 统计添加的资源前缀
     */
    def static final NEW_PREFIX = "better_"
    /**
     *  老的资源前缀，可以保持空字符串(如：R.layout.aaa_layout_aa 会被替换成 R.layout.mae_layout_aa)
     * 如果 OLD_PREFIX 取值为 aaa 的情况
     */
    def static final OLD_PREFIX = "aura_welfare"
}
