package com.coccoc.helloworld2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.coccoc.helloworld2.firstrun.OnBoardData;
import com.coccoc.helloworld2.firstrun.OnBoardPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener,
        ViewPager.OnPageChangeListener, Dialog.OnDismissListener {

    private View mBtnLaunch;
    private LinearLayout mPageIndicator;
    private ViewPager mViewPager;
    private int mPosition;

    private List<OnBoardData> mData = new ArrayList<>();
    private List<View> mDotViews = new ArrayList<>();

    private int mPageAutoScrollCount;
    private boolean mIsLoadingPremium;
    private View mViewLoading;
    private Timer mTimerAutoScroll;

    // Test language
    private List<Language> mLanguageList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.init(getApplication());

        mBtnLaunch = findViewById(R.id.btnLaunch);
        mPageIndicator = findViewById(R.id.indicator);

        mBtnLaunch.setOnClickListener(this::onLaunch);

        mViewLoading = findViewById(R.id.btnLoading);
        mViewPager = findViewById(R.id.viewPager);
        setupOnboardData();
        mViewPager.setVisibility(View.VISIBLE);
        setupOnBoardPager();
        setupPageIndicator();

        // Start with page 0
        onPageSelected(0);
    }

    private void calculate() {
        float x = 0.58f;
        double max = 100;
        double temp, xmax = 0;
        while (x < 10) {
            temp = 2.70158 * Math.pow(x - 1, 3f) + 1.70158 * Math.pow(x - 1, 2) + 1;
            if (temp < max) {
                max = temp;
                xmax = x;
            }
            x += 0.01;
        }
        Log.d("namanh11611", "max = " + max + " - " + xmax);
    }

    private void testJson() {
        String value = "{\n"
                + " \"interval_in_hours\": 10,\n"
                + " \"time\": {\n"
                + "     \"start\": 1,\n"
                + "      \"end\": 1,\n"
                + "      \"interval\": \"0.5\"\n"
                + "  },\n"
                + "\"items\": [\n"
                + "{\n"
                + "     \"name\": \"Jon\",\n"
                + "      \"age\": 12\n"
                + "  },\n"
                + "{\n"
                + "     \"name\": \"Nam\",\n"
                + "      \"age\": 13\n"
                + "  }\n"
                + "]\n"
                + "}";
        DataRefresh dataRefresh = LanguageUtils.parseJson(value, DataRefresh.class);
        if (dataRefresh != null) {
            Log.d("namanh11611", "d " + dataRefresh.getIntervalInHours());
        } else {
            Log.d("namanh11611", "null cmnr");
        }
    }

    private void testString() {
        mLanguageList = new ArrayList<>();
        mLanguageList.add(new Language(0, "Chỉ từ [x], Data 3G/4G Cốc Cốc dành riêng tặng bạn!", "3K"));
        mLanguageList.add(new Language(0, "Data 3G/4G Chỉ từ [x], Cốc Cốc dành riêng tặng bạn!", "3K"));
        mLanguageList.add(new Language(0, "[x] Data 3G/4G Chỉ từ, Cốc Cốc dành riêng tặng bạn!", "3K"));
        mLanguageList.add(new Language(1, "Chỉ từ, Cốc Cốc dành riêng tặng bạn! [x] Data 3G/4G", "3K"));
        mLanguageList.add(new Language(1, "Chỉ từ, Cốc Cốc dành riêng tặng bạn! Data 3G/4G [x]", "3K"));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng Data 3G/4G tặng bạn!", "3K"));
        mLanguageList.add(new Language(1, "Data 3G/4G Cốc Cốc dành riêng tặng bạn!", "3K"));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng tặng bạn Data 3G/4G", "3K"));
        mLanguageList.add(new Language(1, "Data 3G/4G", "3K"));
        mLanguageList.add(new Language(1, "Cốc Cốc [x] dành riêng [x] tặng bạn!", "3K"));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng [x] tặng bạn!", "3K"));
        mLanguageList.add(new Language(1, "[x] Cốc Cốc dành riêng tặng bạn!", "3K"));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng tặng bạn [x]", "3K"));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng tặng bạn!", "3K"));
        mLanguageList.add(new Language(1, "[x]", "3K"));
        mLanguageList.add(new Language(0, "Chỉ từ [x], Data 3G/4G Cốc Cốc dành riêng tặng bạn!", ""));
        mLanguageList.add(new Language(0, "Data 3G/4G Chỉ từ [x], Cốc Cốc dành riêng tặng bạn!", ""));
        mLanguageList.add(new Language(0, "[x] Data 3G/4G Chỉ từ, Cốc Cốc dành riêng tặng bạn!", ""));
        mLanguageList.add(new Language(1, "Chỉ từ, Cốc Cốc dành riêng tặng bạn! [x] Data 3G/4G", ""));
        mLanguageList.add(new Language(1, "Chỉ từ, Cốc Cốc dành riêng tặng bạn! Data 3G/4G [x]", ""));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng Data 3G/4G tặng bạn!", ""));
        mLanguageList.add(new Language(1, "Data 3G/4G Cốc Cốc dành riêng tặng bạn!", ""));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng tặng bạn Data 3G/4G", ""));
        mLanguageList.add(new Language(1, "Data 3G/4G", ""));
        mLanguageList.add(new Language(1, "Cốc Cốc [x] dành riêng [x] tặng bạn!", ""));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng [x] tặng bạn!", ""));
        mLanguageList.add(new Language(1, "[x] Cốc Cốc dành riêng tặng bạn!", ""));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng tặng bạn [x]", ""));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng tặng bạn!", ""));
        mLanguageList.add(new Language(1, "[x]", ""));
        mLanguageList.add(new Language(0, "Chỉ từ [x], Data 3G/4G Cốc Cốc dành riêng tặng bạn!", null));
        mLanguageList.add(new Language(0, "Data 3G/4G Chỉ từ [x], Cốc Cốc dành riêng tặng bạn!", null));
        mLanguageList.add(new Language(0, "[x] Data 3G/4G Chỉ từ, Cốc Cốc dành riêng tặng bạn!", null));
        mLanguageList.add(new Language(1, "Chỉ từ, Cốc Cốc dành riêng tặng bạn! [x] Data 3G/4G", null));
        mLanguageList.add(new Language(1, "Chỉ từ, Cốc Cốc dành riêng tặng bạn! Data 3G/4G [x]", null));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng Data 3G/4G tặng bạn!", null));
        mLanguageList.add(new Language(1, "Data 3G/4G Cốc Cốc dành riêng tặng bạn!", null));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng tặng bạn Data 3G/4G", null));
        mLanguageList.add(new Language(1, "Data 3G/4G", null));
        mLanguageList.add(new Language(1, "Cốc Cốc [x] dành riêng [x] tặng bạn!", null));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng [x] tặng bạn!", null));
        mLanguageList.add(new Language(1, "[x] Cốc Cốc dành riêng tặng bạn!", null));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng tặng bạn [x]", null));
        mLanguageList.add(new Language(1, "Cốc Cốc dành riêng tặng bạn!", null));
        mLanguageList.add(new Language(1, "[x]", null));

//        mRecyclerView = findViewById(R.id.my_recycler_view);
//        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(getApplicationContext(), mLanguageList, this);
//        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View v, int position) {
        Log.d("namanh11611", "on click " + position);
    }

    private void setupPageIndicator() {
        for (int i = 0; i < mData.size(); i++) {
            View dot = LayoutInflater.from(this).inflate(R.layout.coccoc_onboard_dot, mPageIndicator, false);
            mPageIndicator.addView(dot);
            mDotViews.add(dot);
        }
    }

    private void setPageActive(int position) {
        int prevPos = position <= 1 ? 0 : position - 1;
        int nextPos = (position + 1) % mData.size();
        mDotViews.get(prevPos).setBackgroundResource(R.drawable.bg_onboard_dot_inactive);
        mDotViews.get(nextPos).setBackgroundResource(R.drawable.bg_onboard_dot_inactive);
        mDotViews.get(position).setBackgroundResource(R.drawable.bg_onboard_dot_active);
    }

    private void setupOnboardData() {
        mData.add(new OnBoardData(R.drawable.coccoc_onboard_logo, R.string.coccoc_onboard_title_1, R.string.coccoc_onboard_desc_1, "TrackingUtils.TRACK_ONBOARD_ADBLOCK_OPEN"));
        mData.add(new OnBoardData(R.drawable.coccoc_onboard_logo, R.string.coccoc_onboard_title_2, R.string.coccoc_onboard_desc_2, "TrackingUtils.TRACK_ONBOARD_DOWNLOAD_OPEN"));
        mData.add(new OnBoardData(R.drawable.coccoc_onboard_logo, R.string.coccoc_onboard_title_3, R.string.coccoc_onboard_desc_3, "TrackingUtils.TRACK_ONBOARD_NEWS_OPEN"));
    }


    @Override
    public void onDestroy() {
        // Now, user has completely seen the onboard screen. Checked and do not show it again
//        FirstRunSettings.setOnBoardSeen(true);

        // Remove page change listener
        mViewPager.removeOnPageChangeListener(this);
        super.onDestroy();
    }

    private void onLaunch(View v) {
        maybeShowLanguageDialog();
    }

    private void maybeShowLanguageDialog() {
        if (Locale.getDefault().getLanguage().equals(new Locale("vi").getLanguage())) {
            advanceToNextPage();
        } else {
//            BrowserLanguagesDialog dialog = new BrowserLanguagesDialog(this);
//            dialog.setOnDismissListener(this::onDismiss);
//            dialog.show();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        advanceToNextPage();
    }

    private void advanceToNextPage() {
//        if (PremiumUtils.isPremiumVersion()) {
//            requestPremiumPermission();
//        } else {
//            getPageDelegate().advanceToNextPage();
//        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupOnBoardPager() {
        PagerAdapter adapter = new OnBoardPagerAdapter(this, mData);
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
        this.mPosition = position;
        setPageActive(position);
        selectPage(position);

        (new Handler()).postDelayed(() -> {
            if (mViewPager.getAdapter() instanceof OnBoardPagerAdapter) {
                ((OnBoardPagerAdapter) mViewPager.getAdapter()).startAnimation(position);
            }
        }, 1000);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void selectPage(int position) {
        mIsLoadingPremium = position < mData.size() - 1 && mIsLoadingPremium;
        mViewLoading.setVisibility(mIsLoadingPremium ? View.VISIBLE : View.GONE);
        mBtnLaunch.setVisibility(mIsLoadingPremium ? View.GONE : View.VISIBLE);
    }

    private void startAutoScrollViewPager(ViewPager viewPager) {
//        mPageAutoScrollCount = 0;
//        final Handler handler = new Handler();
//        final Runnable Update = () -> {
//            if (mPageAutoScrollCount == mData.size()) {
////                if (viewPager instanceof PremiumViewPager) {
////                    ((PremiumViewPager) viewPager).setPagingEnabled(true);
////                }
//                mTimerAutoScroll.cancel();
//                return;
//            }
//            viewPager.setCurrentItem(mPageAutoScrollCount++, true);
//        };
//
//        mTimerAutoScroll = new Timer();
//        mTimerAutoScroll.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 200, 2000);
    }

}
