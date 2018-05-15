package com.bing.lan.demo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity3 extends AppCompatActivity {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    private float mSelfHeight = 0;//用以判断是否得到正确的宽高值
    private float mTitleScale;

    ImageView mHeadImage;
    AppBarLayout mAppBar;
    Toolbar mToolbar;
    CollapsingToolbarLayout toolbar_layout;

    TabLayout mTabLayout;
    ViewPager mViewPager;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling3);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //https://blog.csdn.net/u012045061/article/details/69568807

        mHeadImage = findViewById(R.id.iv_head);
        mAppBar = findViewById(R.id.app_bar);
        toolbar_layout = findViewById(R.id.toolbar_layout);
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.view_pager);
        toolbar_layout.setTitle(" ");

        final float toolbarHeight = getResources().getDimension(R.dimen.toolbar_height);
        final float initHeight = getResources().getDimension(R.dimen.subscription_head);

        fragments.add(new SimpleFragment());
        titles.add("夏洛克");
        fragments.add(new SimpleFragment());
        titles.add("猫猫猫");

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                log.i("onOffsetChanged()  -------------------------");

                if (mSelfHeight == 0) {
                    mSelfHeight = mTabLayout.getHeight();//55
                    //float distanceTitle = mSubscriptionTitle.getTop() - (toolbarHeight - mSelfHeight) / 2.0f;
                    float distanceTitle = mTabLayout.getTop() + (mSelfHeight - toolbarHeight) / 2.0f;
                    log.i("onOffsetChanged()  mSelfHeight: " + mSelfHeight);
                    log.i("onOffsetChanged()  distanceTitle: " + distanceTitle);

                    mTitleScale = distanceTitle / (initHeight - toolbarHeight);

                    log.i("onOffsetChanged()  mTitleScale: " + mTitleScale);
                }
                mHeadImage.setTranslationY(mTitleScale * verticalOffset);
                mTabLayout.setTranslationY(mTitleScale * verticalOffset);
            }
        });
    }
}
