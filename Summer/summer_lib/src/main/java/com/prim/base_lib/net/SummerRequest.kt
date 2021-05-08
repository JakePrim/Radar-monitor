package com.prim.base_lib.net

import androidx.annotation.IntDef
import java.lang.reflect.Type

/**
 * @desc
 * @author sufulu
 * @time 5/8/21 - 8:44 AM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
open class SummerRequest {
    @METHOD
    var httpMethod: Int = 0
    var headers: MutableMap<String, String>? = null
    var parameters: MutableMap<String, Any>? = null
    var domainUrl: String? = null
    var relativeUrl: String? = null
    var returnType: Type? = null

    /**
     * 约束值
     */
    @IntDef(value = [METHOD.GET, METHOD.POST])
    internal annotation class METHOD {
        companion object {
            const val GET = 0
            const val POST = 1
        }
    }

}