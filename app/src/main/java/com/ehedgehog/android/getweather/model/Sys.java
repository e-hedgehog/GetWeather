package com.ehedgehog.android.getweather.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Sys extends RealmObject {

    @SerializedName("country")
    private String mCountry;
    @SerializedName("sunrise")
    private long mSunrise;
    @SerializedName("sunset")
    private long mSunset;

    public Sys() {
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public long getSunrise() {
        return mSunrise * 1000;
    }

    public void setSunrise(long sunrise) {
        mSunrise = sunrise;
    }

    public long getSunset() {
        return mSunset * 1000;
    }

    public void setSunset(long sunset) {
        mSunset = sunset;
    }
}
