package com.prim.honorkings.biz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.prim.honorkings.R
import com.prim.honorkings.route.RouteFlag

@Route(path = "/profile/authentication", extras = RouteFlag.FLAG_AUTHENTICATION)
class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }
}