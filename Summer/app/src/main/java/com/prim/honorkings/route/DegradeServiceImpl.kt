package com.prim.honorkings.route

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @desc 全局降级服务，当路由的时候目标页面不存在，重定向到统一错误页面
 * @author sufulu
 * @time 4/27/21 - 5:10 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
@Route(path = "/degrade/global/service")
class DegradeServiceImpl : DegradeService {
    override fun init(context: Context?) {
    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        //会调用onLost方法  greenChannel方法会直接跳过拦截器
        ARouter.getInstance().build("/degrade/global/activity").greenChannel().navigation()
    }
}