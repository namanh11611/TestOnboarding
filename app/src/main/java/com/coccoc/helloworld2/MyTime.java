package com.coccoc.helloworld2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyTime extends MyParentTime {
    @SerializedName("start")
    @Expose
    @FieldRequired
    private Integer myStart;

    @SerializedName("end")
    @Expose
    @FieldRequired
    private Integer myEnd;

    public Integer getStart() {
        return myStart;
    }

    public void setStart(Integer myStart) {
        this.myStart = myStart;
    }

    public Integer getEnd() {
        return myEnd;
    }

    public void setEnd(Integer myEnd) {
        this.myEnd = myEnd;
    }
}
