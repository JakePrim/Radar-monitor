package com.prim.summer_common.ui.component

import android.app.Application
import com.google.gson.Gson
import com.prim.base_lib.log.LogConfig
import com.prim.base_lib.log.LogManager
import com.prim.base_lib.log.printer.ConsolePrinter
import com.prim.summer_common.flutter.SummerFlutterCacheManager

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
        //初始化log日志
        initLog()
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
                return "DemoTest"
            }

            override fun enable(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 5
            }


        }, ConsolePrinter())
    }
}