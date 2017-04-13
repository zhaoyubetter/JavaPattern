package com.better.groovy.base

/**
 * Created by zhaoyu on 2017/2/8.
 */
class CharTimes {
    static void main(String[] args) {
        def str = "Hello Groovy, I Like it";
        def times = new int[256];

        for(c in str) {
            times[(int)c] = times[(int)c] + 1;
        }

        for(int i=0; i<str.length(); i++) {
            if(times[i] > 0 ) {
                println((char)i + '次数： ' +  times[i]);
            }
        }
    }
}
