package com.prim.honorkings.demo.tab

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.prim.base_lib.utils.DisplayUtils
import com.prim.honorkings.R
import com.prim.summer_ui.tab.bottom.SummerTabBottomLayout
import com.prim.summer_ui.tab.bottom.TabBottomInfo

class TabBottomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_bottom)
        initTabBottom()
    }

    private fun initTabBottom() {
        val tabBottomLayout: SummerTabBottomLayout = findViewById(R.id.tab_bottom)
        tabBottomLayout.setBottomAlpha(0.85f)
        val bottomInfoList: MutableList<TabBottomInfo<*>> = ArrayList()

        val homeInfo = TabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val categoryInfo = TabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_category),
            null,
            "#ff656667",
            "#ffd44949"
        )
//        val cateInfo = TabBottomInfo(
//            "聊天",
//            "fonts/iconfont.ttf",
//            getString(R.string.if_chat),
//            null,
//            "#ff656667",
//            "#ffd44949"
//        )
        val decodeResource = BitmapFactory.decodeResource(resources, R.drawable.fire, null);
        val cateInfo = TabBottomInfo<String>(
            "聊天",
            decodeResource,
            decodeResource
        )
        val profileInfo = TabBottomInfo(
            "设置",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val meInfo = TabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            "#ff656667",
            "#ffd44949"
        )
        bottomInfoList.add(homeInfo)
        bottomInfoList.add(categoryInfo)
        bottomInfoList.add(cateInfo)
        bottomInfoList.add(profileInfo)
        bottomInfoList.add(meInfo)

        tabBottomLayout.inflateInfo(bottomInfoList)

        tabBottomLayout.addTabSelectedChangeListener { _, _, nextInfo ->
            Toast.makeText(this@TabBottomActivity, nextInfo.name, Toast.LENGTH_LONG).show()
        }

        tabBottomLayout.defaultSelected(homeInfo)
        //改变某个tab的高度 获取某个TAB
        val tabBottom = tabBottomLayout.findTab(bottomInfoList[2])
        //改变高度
        tabBottom?.apply { resetHeight(DisplayUtils.dp2px(66f, resources)) }
    }
}