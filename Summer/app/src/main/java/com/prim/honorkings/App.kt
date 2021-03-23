package com.prim.honorkings

import android.app.Application
import com.google.gson.Gson
import com.prim.base_lib.log.LogConfig
import com.prim.base_lib.log.LogManager
import com.prim.base_lib.log.printer.ConsolePrinter
import com.prim.summer_common.ui.component.SummerBaseApplication

/**
 * @desc
 * @author prim
 * @time 3/22/21 - 9:15 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 * @version 1.0.0
 */
class App : SummerBaseApplication() {
    override fun onCreate() {
        super.onCreate()
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