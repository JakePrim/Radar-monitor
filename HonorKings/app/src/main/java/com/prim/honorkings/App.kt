package com.prim.honorkings

import android.app.Application
import com.prim.base_lib.log.LogConfig
import com.prim.base_lib.log.LogManager

/**
 * @desc
 * @author prim
 * @time 3/22/21 - 9:15 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 * @version 1.0.0
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        LogManager.init(object : LogConfig() {
            override fun getGlobalTag(): String {
                return "DemoTest"
            }

            override fun enable(): Boolean {
                return true
            }
        })
    }
}