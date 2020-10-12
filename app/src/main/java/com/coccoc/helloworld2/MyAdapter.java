package com.coccoc.helloworld2;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private List<Language> mLanguageList;
    private static OnItemClickListener mListener;

    public MyAdapter(Context context, List<Language> languageList, OnItemClickListener listener) {
        mContext = context;
        mLanguageList = languageList;
        mListener = listener;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d("namanh11611", "onCreateViewHolder " + viewType);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
//        Log.d("namanh11611", "onBindViewHolder " + position);
        holder.updateItem(position == 0 ? mLanguageList.get(0) : mLanguageList.get(position - 1));
    }

    @Override
    public int getItemCount() {
        return mLanguageList == null ? 0 : (mLanguageList.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private TextView mId;
        private TextView mName;
        private TextView mCode;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.language_image);
            mId = itemView.findViewById(R.id.language_id);
            mName = itemView.findViewById(R.id.language_name);
            mCode = itemView.findViewById(R.id.language_code);
        }

        public void updateItem(Language language) {
            mImageView.setImageResource(R.drawable.ic_language_global);
            mId.setText(Integer.toString(language.getId()));
            mName.setText(transform(language.getName(), language.getCode()));
            mCode.setText(language.getCode());
        }

        @Override
        public void onClick(View v) {
            Log.d("namanh11611", "click view holder");
            mListener.onItemClick(v, getLayoutPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    // 2 [x] crash
    // Dung cuoi ko an
    private static final String REMOTE_DATA = "Data 3G/4G";
    private Spannable transform(String subTitle, String dataAmount) {
        String detail;
        Spannable spanText;
        if (subTitle.contains("[x]")) {
//            String dataAmount = banner.getDataAmount();
            if (dataAmount == null) dataAmount = "";
            detail = subTitle.replace("[x]", dataAmount);
            if (TextUtils.isEmpty(detail)) return new SpannableString(detail);
            spanText = new SpannableString(detail);

            if (!TextUtils.isEmpty(dataAmount) && detail.contains(dataAmount)) {
                int start = detail.indexOf(dataAmount);
                int length = dataAmount.length();
                int spanColor = mContext.getResources().getColor(R.color.colorAccent);
                spanText.setSpan(new ForegroundColorSpan(spanColor), start, start + length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spanText.setSpan(new StyleSpan(Typeface.BOLD), start, start + length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else {
            detail = subTitle;
            spanText = new SpannableString(detail);
        }

        if (detail.contains(REMOTE_DATA)) {
            int startData = detail.indexOf(REMOTE_DATA);
            int lengthData = REMOTE_DATA.length();
            spanText.setSpan(new StyleSpan(Typeface.BOLD), startData, startData + lengthData,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanText;
    }
}
