package com.coccoc.helloworld2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyParentTime {
    @SerializedName("interval")
    @Expose
    @FieldRequired
    private Integer myInterval;

    public Integer getInterval() {
        return myInterval;
    }

    public void setInterval(Integer myInterval) {
        this.myInterval = myInterval;
    }
}
