package com.coccoc.helloworld2.firstrun;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class OnBoardPagerAdapter extends PagerAdapter {

    private List<OnBoardData> mData;
    private Context mContext;

    public OnBoardPagerAdapter(Context context, List<OnBoardData> data) {
        this.mData = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        OnBoardView view = new OnBoardView(mContext);
        OnBoardData boardData = mData.get(position);
        view.fetchData(boardData);
        container.addView(view);
        return view;
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
