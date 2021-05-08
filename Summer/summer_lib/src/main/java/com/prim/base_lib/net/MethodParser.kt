package com.prim.base_lib.net

import java.lang.IllegalStateException
import java.lang.reflect.Method

/**
 * @desc 方法解析器
 * @author sufulu
 * @time 5/8/21 - 8:56 AM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
class MethodParser(baseUrl: String, method: Method, args: Array<Any>) {

    init {
        //parse method annotations
        parseMethodAnnotations(method)

        //parse method paramters
        parseMethodParameters(method, args)

        //parse return type
        parseMethodReturnType(method)
    }

    private fun parseMethodReturnType(method: Method) {
        if (method.returnType != SummerCall::class) {
            throw IllegalStateException(
                String.format(
                    "method %s must be type of SummerCall.class",
                    method.name
                )
            )
        }
        //获取方法的泛型返回值

    }

    private fun parseMethodParameters(method: Method, args: Array<Any>) {
        TODO("Not yet implemented")
    }

    private fun parseMethodAnnotations(method: Method) {
        TODO("Not yet implemented")
    }

    companion object {
        fun parse(baseUrl: String, method: Method, args: Array<Any>): MethodParser {
            return MethodParser(baseUrl, method, args)
        }
    }
}