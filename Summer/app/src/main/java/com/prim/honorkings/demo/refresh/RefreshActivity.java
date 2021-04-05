package com.prim.honorkings.demo.refresh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.prim.honorkings.R;
import com.prim.summer_ui.refresh.IRefresh;
import com.prim.summer_ui.refresh.SummerRefreshLayout;
import com.prim.summer_ui.refresh.SummerTextOverView;

public class RefreshActivity extends AppCompatActivity {

    private SummerRefreshLayout summerRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        summerRefreshLayout = findViewById(R.id.summer_refresh);
        SummerTextOverView overView = new SummerTextOverView(this);
        summerRefreshLayout.setRefreshOverView(overView);
        summerRefreshLayout.setRefreshListener(new IRefresh.SummerRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        summerRefreshLayout.refreshFinished();
                    }
                },1000);
            }

            @Override
            public boolean enableRefresh() {
                return true;//开启刷新
            }
        });
        summerRefreshLayout.setDisableRefreshScroll(false);
    }
}