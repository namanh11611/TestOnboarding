package com.coccoc.helloworld2.firstrun;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coccoc.helloworld2.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.viewpager.widget.PagerAdapter;

public class OnBoardPagerAdapter extends PagerAdapter {

    private List<OnBoardData> mData;
    private Context mContext;

    private MotionLayout mMotionLayoutBanner;
    private MotionLayout mMotionLayoutSpinFilter;
    private MotionLayout mMotionLayoutSpinData;
    private MotionLayout mMotionLayoutSpinTime;

    public OnBoardPagerAdapter(Context context, List<OnBoardData> data) {
        this.mData = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = null;
        switch (position) {
            case 0:
                v = LayoutInflater.from(mContext).inflate(R.layout.coccoc_first_run_first, container, false);
                mMotionLayoutBanner = v.findViewById(R.id.layoutFilterAdsBannerWrapper);
                mMotionLayoutSpinFilter = v.findViewById(R.id.layoutFilterNumberSpin);
                mMotionLayoutSpinData = v.findViewById(R.id.layoutDataNumberSpin);
                mMotionLayoutSpinTime = v.findViewById(R.id.layoutTimeNumberSpin);
                break;
            case 1:
                v = LayoutInflater.from(mContext).inflate(R.layout.coccoc_first_run_second, container, false);
                break;
            case 2:
                v = LayoutInflater.from(mContext).inflate(R.layout.coccoc_first_run_third, container, false);
                break;
            default:
                break;
        }
        container.addView(v);
        return v;
    }

    public void startAnimation(int position) {
        switch (position) {
            case 0:
                mMotionLayoutBanner.transitionToEnd();
                (new Handler()).postDelayed(() -> {
                    mMotionLayoutSpinFilter.transitionToEnd();
                    mMotionLayoutSpinData.transitionToEnd();
                    mMotionLayoutSpinTime.transitionToEnd();
                }, 2000);
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (object instanceof View) {
            container.removeView((View) object);
        }
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
