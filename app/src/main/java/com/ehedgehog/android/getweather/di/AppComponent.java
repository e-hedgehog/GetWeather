package com.ehedgehog.android.getweather.di;

import com.ehedgehog.android.getweather.screen.WeatherActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class})
public interface AppComponent {

    void injectWeatherActivity(WeatherActivity activity);

}
