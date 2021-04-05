package com.prim.honorkings.fragment

import android.os.Bundle
import android.view.View
import com.prim.honorkings.R
import com.prim.summer_common.flutter.FlutterFragment
import com.prim.summer_common.flutter.SummerFlutterCacheManager

/**
 * @author prim
 * @version 1.0.0
 * @desc Flutter模块
 * @time 3/23/21 - 2:15 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
class RecommendFragment : FlutterFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setTitle("精选推荐")
    }

//    override val moduleName: String?
//        get() = SummerFlutterCacheManager.MODULE_NAME_RECOMMEND

    override fun getLayoutId(): Int {
        return R.layout.fragment_recommend
    }
}