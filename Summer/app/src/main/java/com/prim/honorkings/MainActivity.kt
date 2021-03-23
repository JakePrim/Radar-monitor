package com.prim.honorkings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.prim.honorkings.log.LogActivity
import com.prim.honorkings.tab.TabBottomActivity
import com.prim.summer_ui.tab.bottom.SummerTabBottom
import com.prim.summer_ui.tab.bottom.TabBottomInfo

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.text).setOnClickListener(this)
        val tabBottom = findViewById<SummerTabBottom>(R.id.test_tab)
        val homeInfo = TabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        tabBottom.setTabInfo(homeInfo)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.text -> {
                this.startActivity(Intent(this, TabBottomActivity::class.java))
            }
        }
    }


}