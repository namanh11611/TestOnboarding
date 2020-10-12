package com.coccoc.helloworld2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataRefresh {
    @SerializedName("interval_in_hours")
    @Expose
    @FieldRequired
    private Integer intervalInHours;

    @SerializedName("time")
    @Expose
    @FieldRequired
    private MyTime mMyTime;

    @SerializedName("items")
    @Expose
    @FieldRequired
    private List<MyItem> mMyItems = null;

    public Integer getIntervalInHours() {
        return intervalInHours;
    }

    public void setIntervalInHours(Integer intervalInHours) {
        this.intervalInHours = intervalInHours;
    }

    public MyTime getMyTime() {
        return mMyTime;
    }

    public void setMyTime(MyTime myTime) {
        this.mMyTime = myTime;
    }

    public List<MyItem> getMyItems() {
        return mMyItems;
    }

    public void setMyItems(List<MyItem> myItems) {
        this.mMyItems = myItems;
    }
}
