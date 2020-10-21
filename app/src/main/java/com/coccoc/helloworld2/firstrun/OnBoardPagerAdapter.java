package com.coccoc.helloworld2.firstrun;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.coccoc.helloworld2.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.viewpager.widget.PagerAdapter;

public class OnBoardPagerAdapter extends PagerAdapter {

    private List<OnBoardData> mData;
    private Context mContext;

    private MotionLayout mMotionLayoutAdsBlock;
    private MotionLayout mMotionLayoutSpinFilter;
    private MotionLayout mMotionLayoutSpinData;
    private MotionLayout mMotionLayoutSpinTime;
    private FrameLayout mLayoutSwitch;
    private ImageView mImageSwitchBar;
    private ImageView mImageSwitchDot;

    private MotionLayout mMotionLayoutDownload;
    private MotionLayout mMotionLayoutDownloadProcess;
    private MotionLayout mMotionLayoutDownloadResult;
    private ClipDrawable mClipDownloaded;
    private final Handler mHandlerDownloaded = new Handler();
    private final Runnable mAnimateImageDownloaded = this::doTheAnimation;
    private int mLevel;

    private MotionLayout mMotionLayoutNews;


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
                v = LayoutInflater.from(mContext).inflate(R.layout.coccoc_onboard_ads_block, container, false);
                mMotionLayoutAdsBlock = v.findViewById(R.id.layoutOnboardAdsBlock);
                mMotionLayoutSpinFilter = v.findViewById(R.id.layoutFilterNumberSpin);
                mMotionLayoutSpinData = v.findViewById(R.id.layoutDataNumberSpin);
                mMotionLayoutSpinTime = v.findViewById(R.id.layoutTimeNumberSpin);
                mLayoutSwitch = v.findViewById(R.id.layoutFilterAdsSwitch);
                mImageSwitchBar = v.findViewById(R.id.switchFilterAdsBar);
                mImageSwitchDot = v.findViewById(R.id.switchFilterAdsDot);
                break;
            case 1:
                v = LayoutInflater.from(mContext).inflate(R.layout.coccoc_onboard_download, container, false);
                mMotionLayoutDownload = v.findViewById(R.id.layoutOnboardDownload);
                mMotionLayoutDownloadProcess = v.findViewById(R.id.layoutDownloadProcess);
                mMotionLayoutDownloadResult = v.findViewById(R.id.layoutDownloadResult);
                ImageView imageDownloaded = v.findViewById(R.id.imgDownloaded);
                mClipDownloaded = (ClipDrawable) imageDownloaded.getDrawable();
                break;
            case 2:
                v = LayoutInflater.from(mContext).inflate(R.layout.coccoc_onboard_news, container, false);
                mMotionLayoutNews = v.findViewById(R.id.layoutOnboardNews);
                break;
            default:
                break;
        }
        container.addView(v);
        return v;
    }

    public void startAnimation(int position) {
        int delayTime = position == 0 ? 1000 : 150;
        (new Handler()).postDelayed(() -> {
            switch (position) {
                case 0:
                    mLayoutSwitch.setBackgroundColor(mContext.getResources().getColor(R.color.onboard_switch_background));
//                    mImageSwitchBar.setColorFilter(R.color.onboard_switch_bar);
//                    mImageSwitchDot.setColorFilter(R.color.onboard_switch_dot);
                    int[] a = mMotionLayoutAdsBlock.getConstraintSetIds();
                    for (int i = 0; i < a.length; i++) {
                        Log.d("namanh11612", "a " + i + ": " + a[i]);
                    }
                    Log.d("namanh11612", "Cur state " + mMotionLayoutAdsBlock.getCurrentState());
                    Log.d("namanh11612", "Cur id length = " + a.length);
                    Log.d("namanh11612", "Start state " + mMotionLayoutAdsBlock.getStartState() + " - " + mMotionLayoutAdsBlock.getEndState());

                    mMotionLayoutAdsBlock.transitionToEnd();
                    (new Handler()).postDelayed(() -> {
                        mMotionLayoutSpinFilter.transitionToEnd();
                        mMotionLayoutSpinData.transitionToEnd();
                        mMotionLayoutSpinTime.transitionToEnd();
                    }, 1200);
                    break;
                case 1:
                    mMotionLayoutDownload.rebuildScene();
                    mMotionLayoutDownload.invalidate();
                    mMotionLayoutDownload.requestLayout();
                    mMotionLayoutDownload.transitionToEnd();
                    (new Handler()).postDelayed(() -> {
                        mMotionLayoutDownloadProcess.transitionToEnd();
                    }, 300);
                    (new Handler()).postDelayed(() -> {
                        mClipDownloaded.setLevel(0);
                        mHandlerDownloaded.post(mAnimateImageDownloaded);
                    }, 2100);
                    break;
                case 2:
                    mMotionLayoutNews.transitionToEnd();
                    break;
            }
        }, delayTime);
    }

    public void resetAnimation(int position) {
        switch (position) {
            case 0:
            case 2:
                resetDownloadAnimation();
                break;
            case 1:
                resetAdsBlockAnimation();
                resetNewsAnimation();
                break;
        }
    }

    private void resetAdsBlockAnimation() {
        if (mMotionLayoutAdsBlock == null) return;
        mMotionLayoutAdsBlock.transitionToStart();
        mMotionLayoutSpinFilter.transitionToStart();
        mMotionLayoutSpinData.transitionToStart();
        mMotionLayoutSpinTime.transitionToStart();
    }

    private void resetDownloadAnimation() {
        if (mMotionLayoutDownload == null) return;
        mMotionLayoutDownload.transitionToStart();
    }

    private void resetNewsAnimation() {
        if (mMotionLayoutNews == null) return;
        mMotionLayoutNews.transitionToStart();
    }

    private void doTheAnimation() {
        mLevel += 1000;
        mClipDownloaded.setLevel(mLevel);
        if (mLevel <= 10000) {
            mHandlerDownloaded.postDelayed(mAnimateImageDownloaded, 100);
        } else {
            mHandlerDownloaded.removeCallbacks(mAnimateImageDownloaded);
            mMotionLayoutDownloadResult.transitionToEnd();
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
