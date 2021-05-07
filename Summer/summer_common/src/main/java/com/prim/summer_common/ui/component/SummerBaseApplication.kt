package com.prim.summer_common.ui.component

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.prim.base_lib.log.LogConfig
import com.prim.base_lib.log.LogManager
import com.prim.base_lib.log.SummerLog
import com.prim.base_lib.log.printer.ConsolePrinter
import com.prim.summer_common.BuildConfig
import com.prim.summer_common.manager.ActivityManager

/**
 * @author prim
 * @version 1.0.0
 * @desc Application的基类
 * @time 3/23/21 - 1:44 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
open class SummerBaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化log日志插件
        initLog()
        //初始化Activity管理栈
        ActivityManager.instance.init(this)
        //初始化路由相关信息
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        //预加载flutter引擎模块
//        SummerFlutterCacheManager.instance?.preLoad(this)
    }

    private fun initLog() {
        //初始化日志组件
        LogManager.init(object : LogConfig() {
            override fun injectJsonParser(): JsonParse {
                return JsonParse { src ->
                    Gson().toJson(src)
                }
            }

            override fun getGlobalTag(): String {
                return "SummerLog"
            }

            override fun enable(): Boolean {
                return BuildConfig.DEBUG
            }

            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 5
            }
        }, ConsolePrinter())
    }
}