package com.prim.base_lib.net

/**
 * @desc 请求响应的报文
 * @author sufulu
 * @time 5/8/21 - 8:39 AM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
open class SummerResponse<T> {
    companion object {
        val SUCCESS: Int = 0
    }

    var rawData: String? = null//原始数据
    var code = 0 //业务状态码
    var data: T? = null //业务数据
    var errorData: Map<String, String>? = null //错误状态下的数据
    var msg: String? = null //错误信息
}