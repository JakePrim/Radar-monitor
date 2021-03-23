package com.prim.honorkings.demo.log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.prim.base_lib.log.LogConfig
import com.prim.base_lib.log.LogManager
import com.prim.base_lib.log.LogType
import com.prim.base_lib.log.PLog
import com.prim.base_lib.log.printer.ViewPrinter
import com.prim.honorkings.R

class LogActivity : AppCompatActivity() {

    var viewPrinter: ViewPrinter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        viewPrinter = ViewPrinter(this)
        findViewById<TextView>(R.id.btn_log).setOnClickListener {
            printLog()
        }
        viewPrinter!!.printerProvider.showFloatingView();
    }

    private fun printLog() {
        LogManager.getInstance().addPrinter(viewPrinter)
        PLog.log(object : LogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 5
            }
        }, LogType.E, "------", "55666")
        PLog.a("9000")
    }
}