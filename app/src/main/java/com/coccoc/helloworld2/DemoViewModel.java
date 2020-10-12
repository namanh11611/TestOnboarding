package com.coccoc.helloworld2;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DemoViewModel extends ViewModel {

    private MutableLiveData<String> mStringLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getStringLiveData() {
        return mStringLiveData;
    }

    public void setStringLiveData(String stringLiveData) {
        mStringLiveData.setValue(stringLiveData);
    }
}
