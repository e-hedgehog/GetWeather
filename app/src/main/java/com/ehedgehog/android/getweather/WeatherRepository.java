package com.ehedgehog.android.getweather;

import android.content.Context;
import android.util.Log;

import com.ehedgehog.android.getweather.model.WeatherResponse;
import com.ehedgehog.android.getweather.network.WeatherService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class WeatherRepository {

    public interface Listener {
        void onWeatherLoaded(WeatherResponse weather);

        void onLoadingError(String message);
    }

    private static final String UNITS = "metric";

    private WeatherService mService;
    private Listener mListener;
    private Context mContext;

    public WeatherRepository(WeatherService service, Context context) {
        mService = service;
        mContext = context;
    }

    public void addListener(Listener listener) {
        mListener = listener;
    }

    public Disposable loadWeather(double lat, double lon) {
        return mService.getCurrentWeather(lat, lon, UNITS)
                .flatMap(weatherResponse -> {
                    Log.i("WeatherRepository", weatherResponse == null ? "weather is null" : "not null");
                    Realm.init(mContext);
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        realm.delete(WeatherResponse.class);
                        realm.insert(weatherResponse);
                    });
                    return Observable.just(weatherResponse);
                })
                .onErrorResumeNext(throwable -> {
                    Log.i("WeatherRepository", "error");
                    Log.i("WeatherRepository", throwable.getMessage());
                    Realm.init(mContext);
                    Realm realm = Realm.getDefaultInstance();
                    WeatherResponse response = realm.where(WeatherResponse.class).findFirst();
                    if (response != null)
                        return Observable.just(realm.copyFromRealm(response));
                    return Observable.empty();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(mListener::onWeatherLoaded,
                        throwable -> mListener.onLoadingError(throwable.getMessage()));
    }
}
