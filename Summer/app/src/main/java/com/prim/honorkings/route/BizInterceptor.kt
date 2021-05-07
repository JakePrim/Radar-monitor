package com.prim.honorkings.route

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import java.lang.RuntimeException

/**
 * @desc 路由跳转的拦截器
 * @author sufulu
 * @time 4/27/21 - 4:57 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
//@Route(path = "/biz_interceptor")
@Interceptor(priority = 8, name = "biz_interceptor")
class BizInterceptor : IInterceptor {
    var context: Context? = null
    override fun init(context: Context?) {
        this.context = context
    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        val flag = postcard?.extra
        when {
            (flag?.and(RouteFlag.FLAG_LOGIN)) != 0 -> {
                //表示当前路由跳转需要登录
                //判断是否登录 没有登录拦截
                callback?.onInterrupt(RuntimeException("need login"))
                showToast("请登录")
            }
            (flag.and(RouteFlag.FLAG_AUTHENTICATION)) != 0 -> {
                callback?.onInterrupt(RuntimeException("请授权"))
                showToast("请授权")
            }
            (flag.and(RouteFlag.FLAG_VIP)) != 0 -> {
                callback?.onInterrupt(RuntimeException("请先成为VIP"))
                showToast("请成为VIP")
            }
            else -> {
                //路由通过
                callback?.onContinue(postcard)
            }
        }
    }


    private fun showToast(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

}