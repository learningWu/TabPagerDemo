# TabPagerDemo
将Tab和PagerView整合成一个view

## Download
Gradle:
```
dependencies {
  implementation 'com.eddiezx:tab-pager-view:1.0.3'
}
```

Or Maven:
```
<dependency>
	<groupId>com.eddiezx</groupId>
	<artifactId>tab-pager-view</artifactId>
	<version>1.0.3</version>
	<type>pom</type>
</dependency>
```

## How do I use the library?
```
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

//添加至layout文件
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.eddie.tabpagerview.TabPagerLayout
        android:id="@+id/tab_pager_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:tab_Background="@color/white"
        app:tab_IndicatorColor="@color/tab_IndicatorColor"
        app:tab_SelectedTextColor="@color/tab_IndicatorColor"
        >
    </com.eddie.tabpagerview.TabPagerLayout>
</LinearLayout>
```

