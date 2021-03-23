package com.prim.honorkings.demo.tab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.prim.honorkings.R;
import com.prim.summer_ui.tab.common.ITabLayout;
import com.prim.summer_ui.tab.top.SummerTabTopLayout;
import com.prim.summer_ui.tab.top.TabTopInfo;

import java.util.ArrayList;
import java.util.List;

public class TabTopActivity extends AppCompatActivity {

    String[] tabsStr = new String[]{
            "热门",
            "服装",
            "数码",
            "鞋子",
            "零食",
            "家电",
            "汽车",
            "百货",
            "家居",
            "装修",
            "运动"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_top);
        SummerTabTopLayout summerTabTopLayout = findViewById(R.id.tab_top_view);
        List<TabTopInfo<?>> infoList = new ArrayList<>();
        int defaultColor = getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = getResources().getColor(R.color.tabBottomTintColor);
        for (String s : tabsStr) {
            TabTopInfo<Integer> info = new TabTopInfo<>(s, defaultColor, tintColor);
            infoList.add(info);
        }
        summerTabTopLayout.inflateInfo(infoList);
        summerTabTopLayout.addTabSelectedChangeListener(new ITabLayout.OnTabSelectedListener<TabTopInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, TabTopInfo<?> prevInfo, TabTopInfo<?> nextInfo) {
                Toast.makeText(TabTopActivity.this, nextInfo.name, Toast.LENGTH_SHORT).show();
            }
        });
        summerTabTopLayout.defaultSelected(infoList.get(0));
    }
}