package com.example.ute.models;

import com.google.gson.annotations.SerializedName;

public class Ward {
    @SerializedName("code")
    private String code;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("nameEn")
    private String nameEn;
    
    @SerializedName("districtCode")
    private String districtCode;

    public Ward() {
    }

    public Ward(String code, String name, String nameEn, String districtCode) {
        this.code = code;
        this.name = name;
        this.nameEn = nameEn;
        this.districtCode = districtCode;
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

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    @Override
    public String toString() {
        return name;
    }
}
