package com.prim.honorkings;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.prim.honorkings.logic.MainActivityLogic;
import com.prim.summer_common.ui.component.SummerBaseActivity;

public class MainActivity extends SummerBaseActivity implements MainActivityLogic.ActivityProvider {

    private MainActivityLogic activityLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_bottom);
        activityLogic = new MainActivityLogic(this,savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        activityLogic.onSaveInstanceState(outState);
    }
}