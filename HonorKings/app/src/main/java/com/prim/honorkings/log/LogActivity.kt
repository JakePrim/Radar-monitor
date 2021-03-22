package com.prim.honorkings.log

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.prim.base_lib.log.PLog
import com.prim.honorkings.R

class LogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        findViewById<TextView>(R.id.btn_log).setOnClickListener {
            printLog()
        }
    }

    private fun printLog() {
        PLog.a("9000")
    }
}