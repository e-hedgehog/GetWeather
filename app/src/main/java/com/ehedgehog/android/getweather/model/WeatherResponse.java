package com.ehedgehog.android.getweather.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class WeatherResponse extends RealmObject {

    @SerializedName("weather")
    private RealmList<Weather> mWeather;
    @SerializedName("main")
    private Main mMain;
    @SerializedName("wind")
    private Wind mWind;
    @SerializedName("sys")
    private Sys mSys;
    @SerializedName("timezone")
    private long mTimeZone;
    @SerializedName("name")
    private String mName;

    public WeatherResponse() {
    }

    public Weather getWeather() {
        return mWeather.first();
    }

    public void setWeather(RealmList<Weather> weather) {
        mWeather = weather;
    }

    public Main getMain() {
        return mMain;
    }

    public void setMain(Main main) {
        mMain = main;
    }

    public Wind getWind() {
        return mWind;
    }

    public void setWind(Wind wind) {
        mWind = wind;
    }

    public Sys getSys() {
        return mSys;
    }

    public void setSys(Sys sys) {
        mSys = sys;
    }

    public long getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(long timeZone) {
        mTimeZone = timeZone;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
