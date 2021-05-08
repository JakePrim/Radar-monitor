package com.prim.base_lib.net

/**
 * @desc 拦截器
 * @author sufulu
 * @time 5/8/21 - 8:50 AM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */

interface SummerInterceptor {
    fun intercept(chain: Chain): Boolean

    /**
     * Chain 对象会在派发拦截器的时候创建
     */
    interface Chain {
        /**
         * 判断是不是在request阶段
         */
        val isRequestPeriod: Boolean get() = false

        fun request(): SummerRequest

        /**
         * response对象在网络发起之前是为空的
         */
        fun response(): SummerResponse<*>?
    }
}