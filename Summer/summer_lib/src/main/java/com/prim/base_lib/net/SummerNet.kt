package com.prim.base_lib.net

import java.lang.reflect.Proxy

/**
 * @desc 网络请求的入口类
 * @author sufulu
 * @time 5/8/21 - 8:52 AM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
open class SummerNet constructor(val baseUrl: String, callFactory: SummerCall.Factory) {
    /**
     * 拦截器集合
     */
    private var interceptors: MutableList<SummerInterceptor> = mutableListOf()

    /**
     * 添加拦截器
     */
    fun addInterceptors(interceptor: SummerInterceptor) {
        interceptors.add(interceptor)
    }

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { proxy, method, args ->
        } as T
    }
}