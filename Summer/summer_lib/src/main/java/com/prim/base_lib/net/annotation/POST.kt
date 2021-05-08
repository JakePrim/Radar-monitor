package com.prim.base_lib.net.annotation

/**
 * @desc
 * @author sufulu
 * @time 5/8/21 - 8:33 AM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class POST(val value: String, val formPost: Boolean = true)
