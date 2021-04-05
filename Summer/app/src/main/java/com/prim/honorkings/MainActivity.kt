package com.prim.honorkings

import android.os.Bundle
import com.prim.honorkings.logic.MainActivityLogic
import com.prim.honorkings.logic.MainActivityLogic.ActivityProvider
import com.prim.summer_common.ui.component.SummerBaseActivity

class MainActivity : SummerBaseActivity(), ActivityProvider {
    private var activityLogic: MainActivityLogic? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_bottom)
        activityLogic = MainActivityLogic(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        activityLogic?.onSaveInstanceState(outState)
    }
}