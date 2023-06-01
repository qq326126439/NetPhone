package com.lx.net.common.netstate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***********************************************************************
 * <p>@description: 作用于方法之上，运行时注解
 * <p>@author: lanm
 * <p>@created on: 2019/6/18
 * <p>@version: 1.0.0
 * <p>@modify time:
 **********************************************************************/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Network {
    NetType netType() default NetType.AUTO;
}
