package com.prim.base_lib.net.annotation

/**
 * @desc fun test(@Filed("province") int provinceId)
 * @author sufulu
 * @time 5/8/21 - 8:31 AM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Filed(val value:String)
