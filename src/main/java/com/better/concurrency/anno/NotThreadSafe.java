package com.better.concurrency.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface NotThreadSafe {
}
