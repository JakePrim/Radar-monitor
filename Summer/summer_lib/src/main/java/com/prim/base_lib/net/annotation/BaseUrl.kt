package com.prim.base_lib.net.annotation

import java.lang.annotation.RetentionPolicy


/**
 * @desc @BaseUrl("")
 * @author sufulu
 * @time 5/8/21 - 8:29 AM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl(val value:String)
