package com.better.groovy.base.xml

import groovy.xml.MarkupBuilder

/**
 * 创建xml
 * Created by zhaoyu on 2017/4/17.
 */

// 创建xml
def mB = new MarkupBuilder(new File('book.xml').newPrintWriter())
mB.book() {     // 伪方法
    author('大神')
    title('Groovy 开发与晋级')
    publisher('清华大学')
    isbn(number: '123213123')
}

def data = [
        '11111': ['Groovy', '小明', '清华大学'],
        '22222': ['Android开发', '小明', '人民大学'],
        '33333': ['Java并发', '小张', '湖南大学']
]

def mB2 = new MarkupBuilder(new File('lib.xml').newPrintWriter())
def lib = mB2.library() {
    data.each { bk ->
        mB2.book() {
            author(bk.value[1])
            title(bk.value[0])
            publisher(bk.value[2])
            isbn(number: bk.key)
        }
    }
}

/*
<book>
  <author>大神</author>
  <title>Groovy 开发与晋级</title>
  <publisher>清华大学</publisher>
  <isbn number='123213123' />
</book>
 */