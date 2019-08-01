package com.ehedgehog.android.getweather;

import android.app.Application;

import com.ehedgehog.android.getweather.di.AppComponent;
import com.ehedgehog.android.getweather.di.DaggerAppComponent;
import com.ehedgehog.android.getweather.di.DataModule;

import androidx.annotation.NonNull;

public class AppDelegate extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent.builder()
                .dataModule(new DataModule(this))
                .build();
    }

    @NonNull
    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
