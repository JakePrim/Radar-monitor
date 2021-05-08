package com.prim.base_lib.net.annotation

/**
 * @desc 占位替换
 * @GET("/cities/{province})
 * fun test(@Path("province") int provinceId)
 * @author sufulu
 * @time 5/8/21 - 8:35 AM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(val value:String)
