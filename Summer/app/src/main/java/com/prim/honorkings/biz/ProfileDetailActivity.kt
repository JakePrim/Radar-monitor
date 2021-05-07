package com.prim.honorkings.biz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.prim.honorkings.R
import com.prim.honorkings.route.RouteFlag

/**
 * 个人中心页面需要登录
 */
@Route(path = "/profile/detail", extras = RouteFlag.FLAG_LOGIN)
class ProfileDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)
    }
}