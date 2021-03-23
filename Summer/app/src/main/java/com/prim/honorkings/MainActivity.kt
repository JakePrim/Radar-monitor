package com.prim.honorkings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.prim.honorkings.demo.log.LogActivity
import com.prim.honorkings.demo.tab.TabBottomActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.test_log).setOnClickListener(this)
        findViewById<TextView>(R.id.test_ui).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.test_log -> {
                this.startActivity(Intent(this, LogActivity::class.java))
            }
            R.id.test_ui -> {
                this.startActivity(Intent(this, TabBottomActivity::class.java))
            }
        }
    }


}