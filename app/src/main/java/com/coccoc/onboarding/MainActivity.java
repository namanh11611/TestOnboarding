package com.coccoc.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public static final int NUMBER_PAGE = 3;

    private View mBtnLaunch;
    private LinearLayout mPageIndicator;
    private ViewPager mViewPager;

    private List<View> mDotViews = new ArrayList<>();

    private boolean mIsLoadingPremium;
    private View mViewLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnLaunch = findViewById(R.id.btnLaunch);
        mPageIndicator = findViewById(R.id.indicator);

        mViewLoading = findViewById(R.id.btnLoading);
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setVisibility(View.VISIBLE);
        setupOnBoardPager();
        setupPageIndicator();

        // Start with page 0
        onPageSelected(0);

    }

    private void setupPageIndicator() {
        for (int i = 0; i < NUMBER_PAGE; i++) {
            View dot = LayoutInflater.from(this).inflate(R.layout.coccoc_onboard_dot, mPageIndicator, false);
            mPageIndicator.addView(dot);
            mDotViews.add(dot);
        }
    }

    private void setPageActive(int position) {
        int prevPos = position <= 1 ? 0 : position - 1;
        int nextPos = (position + 1) % NUMBER_PAGE;
        mDotViews.get(prevPos).setBackgroundResource(R.drawable.bg_onboard_dot_inactive);
        mDotViews.get(nextPos).setBackgroundResource(R.drawable.bg_onboard_dot_inactive);
        mDotViews.get(position).setBackgroundResource(R.drawable.bg_onboard_dot_active);
    }


    @Override
    public void onDestroy() {
        // Remove page change listener
        mViewPager.removeOnPageChangeListener(this);
        super.onDestroy();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupOnBoardPager() {
        PagerAdapter adapter = new OnBoardPagerAdapter(this);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
        mIsLoadingPremium = true;
        startAutoScrollViewPager(mViewPager);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        setPageActive(position);
        selectPage(position);

        if (mViewPager.getAdapter() instanceof OnBoardPagerAdapter) {
            ((OnBoardPagerAdapter) mViewPager.getAdapter()).startAnimation(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void selectPage(int position) {
        mIsLoadingPremium = position < NUMBER_PAGE - 1 && mIsLoadingPremium;
        mViewLoading.setVisibility(mIsLoadingPremium ? View.VISIBLE : View.GONE);
        mBtnLaunch.setVisibility(mIsLoadingPremium ? View.GONE : View.VISIBLE);
    }

    private void startAutoScrollViewPager(ViewPager viewPager) {
        (new Handler()).postDelayed(() -> {
            viewPager.setCurrentItem(1, true);
        }, 3000);

        (new Handler()).postDelayed(() -> {
            viewPager.setCurrentItem(2, true);
        }, 7000);
    }

}
