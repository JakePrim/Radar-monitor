package com.prim.base_lib.net

import java.io.IOException

/**
 * @desc
 * @author sufulu
 * @time 5/8/21 - 8:43 AM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
interface SummerCall<T> {
    @Throws(IOException::class)
    fun execute(): SummerResponse<T>

    fun enqueue(callback: SummerNetCallback<T>)

    interface Factory {
        fun newCall(request:SummerRequest):SummerCall<*>
    }
}