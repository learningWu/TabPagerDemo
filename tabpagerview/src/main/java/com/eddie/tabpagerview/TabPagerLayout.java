package com.eddie.tabpagerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;


public class TabPagerLayout extends FrameLayout {
    private TabLayout tabLayout;
    private ViewPager vp;

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTabs = new ArrayList<>();
    private int mInitPosition;
    private View mCustomView;
    private OnTabSelectListener mOnTabClickListener;

    public TabPagerLayout(Context context) {
        this(context, null);
    }

    public TabPagerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TabPagerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttrs(context, attrs);
    }

    private void init() {
        //Inflate布局 (加载过程)
        LayoutInflater.from(getContext()).inflate(R.layout.tab_pager_layout, this, true);
        findAllViews(); //找到所有的view

    }

    /* 初始化相关属性，引入相关属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        //标题的默认背景颜色
        int defaultTabBackground = context.getResources().getColor(R.color.default_tab_background);
        //默认指示颜色
        int defaultTabIndicatorColor = context.getResources().getColor(R.color.default_tab_indicator_color);
        //默认高度
        int defaultTabIndicatorHeight = (int) context.getResources().getDimension(R.dimen.default_tab_indicator_height);
        //未选中颜色
        int defaultTabUnselectedTextColor = context.getResources().getColor(R.color.default_tab_unselected_text_color);
        //选中颜色
        int defaultTabSelectedTextColor = context.getResources().getColor(R.color.default_tab_selected_text_color);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabPagerLayout);
        int tabBackground = typedArray.getColor(R.styleable.TabPagerLayout_tab_Background, defaultTabBackground);
        int tabIndicatorColor = typedArray.getColor(R.styleable.TabPagerLayout_tab_IndicatorColor, defaultTabIndicatorColor);
        int tabIndicatorHeight = typedArray.getColor(R.styleable.TabPagerLayout_tab_IndicatorHeight, defaultTabIndicatorHeight);
        int tabSelectedTextColor = typedArray.getColor(R.styleable.TabPagerLayout_tab_SelectedTextColor, defaultTabSelectedTextColor);
        int tabUnselectedTextColor =typedArray.getColor(R.styleable.TabPagerLayout_tab_UnselectedTextColor, defaultTabUnselectedTextColor);
        //背景颜色
        tabLayout.setBackgroundColor(tabBackground);
        //指示器颜色
        tabLayout.setSelectedTabIndicatorColor(tabIndicatorColor);
        //指示器高度
        tabLayout.setSelectedTabIndicatorHeight(tabIndicatorHeight);
        //字体颜色
        tabLayout.setTabTextColors(tabUnselectedTextColor, tabSelectedTextColor);
    }

    private void findAllViews() {
        tabLayout = findViewById(R.id.tab_layout);
        vp = findViewById(R.id.vp);
    }

    public void setInitPosition(int initPosition) {
        mInitPosition = initPosition;
    }

    public void create() {
        addTab();
        vp.setAdapter(new FragmentPagerAdapter(((FragmentActivity) getContext()).getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                vp.setCurrentItem(position);
                if (mOnTabClickListener != null) {
                    mOnTabClickListener.onTabSelect(position);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //跳转至初始位置
        TabLayout.Tab tab = tabLayout.getTabAt(mInitPosition);
        if (tab != null) {
            tab.select();
        }
    }

    /**
     * 添加
     *
     * @param fragment 显示的fragment
     * @param tab      对应的tab描述内容
     */
    public TabPagerLayout addFragmentAndTab(Fragment fragment, String tab) {
        mFragments.add(fragment);
        mTabs.add(tab);
        return this;
    }

    public TabPagerLayout setCustomTabLayout(View customTabLayout) {
        mCustomView = customTabLayout;
        return this;
    }

    public TabPagerLayout setOnTabClickListener(OnTabSelectListener onTabClickListener) {
        mOnTabClickListener = onTabClickListener;
        return this;
    }

    /**
     * 添加tab title
     */
    private void addTab() {

        for (int i = 0; i < mTabs.size(); i++) {
            String str = mTabs.get(i);
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(str);
            //有自定义layout，使用自定义
            if (mCustomView != null) {
                tab.setCustomView(mCustomView);
            }
            tabLayout.addTab(tab);
        }
    }

    /**
     * tab切换回调
     */
    public interface OnTabSelectListener {
        void onTabSelect(int position);
    }
}
