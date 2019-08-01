package com.ehedgehog.android.getweather.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Main extends RealmObject {

    @SerializedName("temp")
    private float mTemp;
    @SerializedName("pressure")
    private float mPressure;
    @SerializedName("humidity")
    private int mHumidity;

    public Main() {
    }

    public float getTemp() {
        return mTemp;
    }

    public void setTemp(float temp) {
        mTemp = temp;
    }

    public float getPressure() {
        return mPressure;
    }

    public void setPressure(float pressure) {
        mPressure = pressure;
    }

    public int getHumidity() {
        return mHumidity;
    }

    public void setHumidity(int humidity) {
        mHumidity = humidity;
    }
}
