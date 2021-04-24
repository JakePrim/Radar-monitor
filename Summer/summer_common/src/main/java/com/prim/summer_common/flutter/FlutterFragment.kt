package com.prim.summer_common.flutter

import android.content.Context
import android.os.Bundle
import android.view.View
import com.prim.summer_common.R
import com.prim.summer_common.ui.component.SummerBaseFragment
//import io.flutter.embedding.android.FlutterTextureView
//import io.flutter.embedding.android.FlutterView
//import io.flutter.embedding.engine.FlutterEngine
//import io.flutter.embedding.engine.dart.DartExecutor
import java.lang.reflect.Array.get

/**
 * @desc
 * @author prim 实现FlutterFragment
 * @time 3/28/21 - 12:13 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 * @version 1.0.0
 */
abstract class FlutterFragment : SummerBaseFragment(){
//    private val flutterEngine: FlutterEngine?
//
//    protected var flutterView: FlutterView? = null
//
//    abstract val moduleName : String?
//
//    init {
//        flutterEngine = null
////        flutterEngine = SummerFlutterCacheManager.instance!!.getCachedFlutterEngine(moduleName!!,com.prim.summer_common.AppGlobals.get())
//    }
//
//    override fun getLayoutId(): Int {
//        return R.layout.fragment_flutter
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        //将flutterView 添加进去
//    }
//
//    fun setTitle(titleStr:String){
////        title.text = titleStr;
//    }
//
//    private fun createFlutterView(context:Context) :FlutterView{
//        //使用FlutterTextureView来进行渲染，以规避FlutterSurfaceView压后台回来后界面被复用的问题
//        val flutterTextureView = FlutterTextureView(activity!!)
//        flutterView = FlutterView(context,flutterTextureView)
//        return flutterView!!
//    }
//
//    //将生命周期传递给flutterView 感知APP
//    override fun onStart() {
//        flutterView!!.attachToFlutterEngine(flutterEngine!!)
//        super.onStart()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        //flutter >= 1.17
//        flutterEngine!!.lifecycleChannel.appIsResumed()
//    }
//
//
//    override fun onPause() {
//        super.onPause()
//        flutterEngine!!.lifecycleChannel.appIsInactive()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        flutterEngine!!.lifecycleChannel.appIsPaused()
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        flutterEngine!!.lifecycleChannel.appIsDetached()
//    }

}