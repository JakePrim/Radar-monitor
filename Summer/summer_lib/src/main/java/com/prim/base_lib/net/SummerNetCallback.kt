package com.prim.base_lib.net

/**
 * @desc 网络请求回调
 * @author sufulu
 * @time 5/8/21 - 8:38 AM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
interface SummerNetCallback<T> {
    fun onSuccess(response: SummerResponse<T>)
    fun onFailed(throwable: Throwable)
}