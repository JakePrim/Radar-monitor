package com.prim.honorkings.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import com.alibaba.android.arouter.launcher.ARouter
import com.prim.base_lib.log.SummerLog
import com.prim.honorkings.R
import com.prim.honorkings.demo.kotlin.CoroutineScene
import com.prim.summer_common.ui.component.SummerBaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 3/23/21 - 2:15 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
class HomePageFragment : SummerBaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_detail.setOnClickListener {
//            navigation("/profile/detail")
//            CoroutineScene.startScene2()
            // 不推荐使用GlobalScope lifecycleScope和宿主的生命周期绑定
            lifecycleScope.launch {
                activity?.let { it1 ->
                    val result = CoroutineScene.parseAssetsFile(it1.assets, "config.json")
                    SummerLog.e(result)
                }
            }
            lifecycleScope.launchWhenCreated {
                //当生命周期至少为oncreate的时候 才会启动
                whenCreated {
                    //当生命周期为oncreate才会执行，否则暂停
                }
                whenResumed {
                    //当生命周期为onresume才会执行 否则暂停
                }
            }

            lifecycleScope.launchWhenResumed {
                //宿主的生命周期至少为onResumed的时候才会启动
            }
        }
        btn_authentication.setOnClickListener {
            navigation("/profile/authentication")
        }
        btn_vip.setOnClickListener {
            navigation("/profile/vip")
        }
        btn_degrade.setOnClickListener {
            //路由到一个不存在的页面
            navigation("/xx/xxx")
        }
    }

    private fun navigation(path: String) {
        ARouter.getInstance().build(path).navigation()
    }
}