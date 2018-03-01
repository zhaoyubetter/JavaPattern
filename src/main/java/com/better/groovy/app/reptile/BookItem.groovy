package com.better.groovy.app.reptile

import groovy.transform.Canonical

/**
 * Created by cz on 2017/1/25.
 */
@Canonical
class BookItem {
    String book
    String type
    String detailUrl
    List url

    BookItem(String book, String type, String detailUrl) {
        this.book = book
        this.type = type
        this.detailUrl = detailUrl
        this.url=[]
    }

}
