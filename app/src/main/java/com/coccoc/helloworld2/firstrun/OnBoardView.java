package com.coccoc.helloworld2.firstrun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.coccoc.helloworld2.R;

import androidx.annotation.NonNull;

public class OnBoardView extends FrameLayout {

    private ImageView mImageView;
    private TextView mTvTitle;
    private TextView mTvDesc;

    public OnBoardView(@NonNull Context context) {
        super(context);
        inflateLayout(context);
    }

    public void fetchData(OnBoardData data) {
        mImageView.setImageResource(data.imageId);
        mTvTitle.setText(data.titleId);
        mTvDesc.setText(data.descId);
    }

    private void inflateLayout(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.coccoc_view_onboard, this, false);
        mImageView = v.findViewById(R.id.imageView);
        mTvTitle = v.findViewById(R.id.tvTitle);
        mTvDesc = v.findViewById(R.id.tvDesc);
        addView(v);
    }
}
