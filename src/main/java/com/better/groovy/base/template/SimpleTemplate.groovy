package com.better.groovy.base.template

import groovy.text.SimpleTemplateEngine

/**
 * Created by zhaoyu on 2017/4/24.
 */

def file = new File('book.template')
def binding = ['author': 'Json',
               'title' : 'groovy开发实战',
               'isbn'  : '19880909']        // bind 是一个map
def engine = new SimpleTemplateEngine()     // SimpleTemplateEngine
def template = engine.createTemplate(file)  // 基于文件创建模板
def writable = template.make(binding)
println(writable)
