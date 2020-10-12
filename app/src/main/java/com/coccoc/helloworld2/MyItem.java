package com.coccoc.helloworld2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyItem {
    @SerializedName("name")
    @Expose
    @FieldRequired
    private String name;

    @SerializedName("age")
    @Expose
    @FieldRequired
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
