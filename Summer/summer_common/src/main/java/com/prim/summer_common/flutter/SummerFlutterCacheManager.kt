package com.prim.summer_common.flutter

import android.content.Context
import android.os.Looper
//import io.flutter.embedding.engine.FlutterEngine
//import io.flutter.embedding.engine.FlutterEngineCache
//import io.flutter.embedding.engine.dart.DartExecutor
//import io.flutter.view.FlutterMain

/**
 * @desc Flutter优化提升加载速度，实现秒开Flutter模块
 * 1. 让加载不损失首页性能
 * 2. 实例多个Flutter引擎将在不同的dart 入口文件
 * @author prim
 * @time 3/28/21 - 3:03 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 * @version 1.0.0
 */
class SummerFlutterCacheManager private constructor() {
//    /**
//     * 预加载Flutter
//     */
//    fun preLoad(context: Context){
//        //在线程空闲时加载flutter
//        //获取空闲的Handler
//        Looper.myQueue().addIdleHandler {
//            initFlutterEngine(context, MODULE_NAME_FAVORITE)
//            initFlutterEngine(context, MODULE_NAME_RECOMMEND)
//            false
//        }
//    }
//
//    /**
//     * 获取预加载的Flutter
//     */
//    fun getCachedFlutterEngine(moduleName:String,context: Context?) : FlutterEngine {
//        var engine:FlutterEngine? = FlutterEngineCache.getInstance()[moduleName]
//        if (engine == null && context != null){
//            engine = initFlutterEngine(context,moduleName)
//        }
//        return engine!!
//    }
//
//    /**
//     * 初始化flutter
//     */
//    private fun initFlutterEngine(context: Context, module: String):FlutterEngine {
//        var flutterEngine = FlutterEngine(context)
//        flutterEngine.dartExecutor.executeDartEntrypoint(
//            //加载不同Flutter Module的dart
//            DartExecutor.DartEntrypoint(FlutterMain.findAppBundlePath(),module)
//        )
//        FlutterEngineCache.getInstance().put(module,flutterEngine)
//        return flutterEngine
//    }
//
//    companion object {
//        const val MODULE_NAME_FAVORITE = "main"
//        const val MODULE_NAME_RECOMMEND = "recommend"
//        @JvmStatic
//        @get:Synchronized
//        var instance: SummerFlutterCacheManager? = null
//            get() {
//                if (field == null) {
//                    field = SummerFlutterCacheManager()
//                }
//                return field
//            }
//
//    }
}