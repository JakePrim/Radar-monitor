package com.prim.honorkings.route

/**
 * @author sufulu
 * @version 1.0.0
 * @desc 路由跳转的flag
 * @time 4/27/21 - 4:41 PM
 * @contact sufululove@gmail.com
 * @name Summer
 */
object RouteFlag {
    /**
     * 标记该路由跳转需要登录
     */
    const val FLAG_LOGIN: Int = 0x01

    /**
     * 标记该路由跳转需要授权
     */
    const val FLAG_AUTHENTICATION = FLAG_LOGIN shl 1

    /**
     * 标记该路由跳转需要是VIP
     */
    const val FLAG_VIP = FLAG_AUTHENTICATION shl 1
}