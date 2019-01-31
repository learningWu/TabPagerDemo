package com.eddie.tabpagerdemo;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.eddie.tabpagerview.TabPagerLayout;

public class MainActivity extends AppCompatActivity {
    TabPagerLayout tabPagerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabPagerLayout = findViewById(R.id.tab_pager_layout);
        tabPagerLayout.addFragmentAndTab(new Fragment1(), "第一个fragment")
                .addFragmentAndTab(new Fragment2(), "第二个fragment")
                .setOnTabClickListener(new TabPagerLayout.OnTabSelectListener() {
                    @Override
                    public void onTabSelect(int position) {
                        Toast.makeText(MainActivity.this, "选中" + position + "的tab", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
    }
}
