package com.better.groovy.forandroid.refactor;


import com.better.groovy.forandroid.refactor.folder.LayoutReplace;

import java.io.IOException;

public class HelloGo {

    public static void main(String[] args) throws IOException {
        LayoutReplace layoutReplace = new LayoutReplace("", "");
        layoutReplace.replaceThis();
    }
}
