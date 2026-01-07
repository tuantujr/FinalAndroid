package com.example.ute.models;

import com.google.gson.annotations.SerializedName;

public class Province {
    @SerializedName("code")
    private String code;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("nameEn")
    private String nameEn;

    public Province() {
    }

    public Province(String code, String name, String nameEn) {
        this.code = code;
        this.name = name;
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @Override
    public String toString() {
        return name;
    }
}
