package com.bing.lan.demo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class ScrollingActivity2 extends AppCompatActivity {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    private float mSelfHeight = 0;//用以判断是否得到正确的宽高值
    private float mTitleScale;
    private float mSubScribeScale;
    private float mSubScribeScaleX;
    private float mHeadImgScale;

    ImageView mHeadImage;
    TextView mSubscriptionTitle;
    TextView mSubscribe;
    AppBarLayout mAppBar;
    Toolbar mToolbar;
    CollapsingToolbarLayout toolbar_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling2);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //https://blog.csdn.net/u012045061/article/details/69568807

        mHeadImage = findViewById(R.id.iv_head);
        mSubscriptionTitle = findViewById(R.id.subscription_title);
        mSubscribe = findViewById(R.id.subscribe);
        mAppBar = findViewById(R.id.app_bar);
        toolbar_layout = findViewById(R.id.toolbar_layout);
        toolbar_layout.setTitle(" ");

        final float screenW = getResources().getDisplayMetrics().widthPixels;
        final float toolbarHeight = getResources().getDimension(R.dimen.toolbar_height);
        final float initHeight = getResources().getDimension(R.dimen.subscription_head);

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //2.625
                log.i("onOffsetChanged()  -------------------------");
                //log.i("onOffsetChanged()  toolbarHeight: " + toolbarHeight);//105
                //log.i("onOffsetChanged()  initHeight: " + initHeight);//1050


                if (mSelfHeight == 0) {
                    mSelfHeight = mSubscriptionTitle.getHeight();//55
                    //float distanceTitle = mSubscriptionTitle.getTop() - (toolbarHeight - mSelfHeight) / 2.0f;
                    float distanceTitle = mSubscriptionTitle.getTop() + (mSelfHeight - toolbarHeight) / 2.0f;
                    float distanceSubscribe = mSubscribe.getY() + (mSubscribe.getHeight() - toolbarHeight) / 2.0f;
                    float distanceHeadImg = mHeadImage.getY() + (mHeadImage.getHeight() - toolbarHeight) / 2.0f;
                    float distanceSubscribeX = screenW / 2.0f - (mSubscribe.getWidth() / 2.0f
                            + getResources().getDimension(R.dimen.normal_space));
                    log.i("onOffsetChanged()  mSelfHeight: " + mSelfHeight);
                    log.i("onOffsetChanged()  distanceTitle: " + distanceTitle);
                    log.i("onOffsetChanged()  distanceSubscribe: " + distanceSubscribe);
                    log.i("onOffsetChanged()  distanceHeadImg: " + distanceHeadImg);
                    log.i("onOffsetChanged()  distanceSubscribeX: " + distanceSubscribeX);

                    mTitleScale = distanceTitle / (initHeight - toolbarHeight);
                    mSubScribeScale = distanceSubscribe / (initHeight - toolbarHeight);
                    mHeadImgScale = distanceHeadImg / (initHeight - toolbarHeight);
                    mSubScribeScaleX = distanceSubscribeX / (initHeight - toolbarHeight);

                    log.i("onOffsetChanged()  ---------------- ");
                    log.i("onOffsetChanged()  mTitleScale: " + mTitleScale);
                    log.i("onOffsetChanged()  mSubScribeScale: " + mSubScribeScale);
                    log.i("onOffsetChanged()  mHeadImgScale: " + mHeadImgScale);
                    log.i("onOffsetChanged()  mSubScribeScaleX: " + mSubScribeScaleX);
                }
                float scale = 1.0f - (-verticalOffset) / (initHeight - toolbarHeight);
                mHeadImage.setScaleX(scale);
                mHeadImage.setScaleY(scale);
                mHeadImage.setTranslationY(mHeadImgScale * verticalOffset);
                mSubscriptionTitle.setTranslationY(mTitleScale * verticalOffset);
                mSubscribe.setTranslationY(mSubScribeScale * verticalOffset);
                mSubscribe.setTranslationX(-mSubScribeScaleX * verticalOffset);

                log.i("onOffsetChanged()  mSubscriptionTitle.getTop(): " + (mSubscriptionTitle.getTop() + mSubscriptionTitle.getTranslationY()));
            }
        });
    }
}
