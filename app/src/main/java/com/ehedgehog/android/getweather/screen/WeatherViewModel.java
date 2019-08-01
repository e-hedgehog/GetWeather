package com.ehedgehog.android.getweather.screen;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import com.ehedgehog.android.getweather.BR;
import com.ehedgehog.android.getweather.WeatherRepository;
import com.ehedgehog.android.getweather.WeatherUtils;
import com.ehedgehog.android.getweather.model.WeatherResponse;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import io.reactivex.disposables.Disposable;

public class WeatherViewModel extends BaseObservable {

    private static final String TAG = WeatherViewModel.class.getSimpleName();

    private WeatherRepository mRepository;
    private FusedLocationProviderClient mLocationProvider;

    private WeatherResponse mWeather;

    private boolean mIsLoading = false;

    private Disposable mWeatherSubscription;

    public WeatherViewModel(WeatherRepository repository, FusedLocationProviderClient locationProvider) {
        mRepository = repository;
        mLocationProvider = locationProvider;
    }

    public void setupRepository() {
        mRepository.addListener(new WeatherRepository.Listener() {
            @Override
            public void onWeatherLoaded(WeatherResponse weather) {
                mWeather = weather;
                Log.i(TAG, mWeather == null ? "weather is null" : "weather not null");
                notifyPropertyChanged(BR.locationName);
                notifyPropertyChanged(BR.temp);
                notifyPropertyChanged(BR.description);
                notifyPropertyChanged(BR.pressure);
                notifyPropertyChanged(BR.humidity);
                notifyPropertyChanged(BR.wind);
                notifyPropertyChanged(BR.sunrise);
                notifyPropertyChanged(BR.sunset);
                notifyPropertyChanged(BR.iconId);
                setLoading(false);
                if (mWeatherSubscription != null && !mWeatherSubscription.isDisposed())
                    mWeatherSubscription.dispose();
            }

            @Override
            public void onLoadingError(String message) {
                Log.e("WeatherViewModel", message);
                setLoading(false);
            }
        });
    }

    public void loadWeather() {
        mLocationProvider.getLastLocation()
                .addOnSuccessListener(location -> mWeatherSubscription =
                        mRepository.loadWeather(location.getLatitude(), location.getLongitude()));
    }

    public void onRefresh(Context context) {
        if (WeatherUtils.isOnline(context)) {
            setLoading(true);
            loadWeather();
        } else
            setLoading(false);
    }

    @Bindable
    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean isLoading) {
        mIsLoading = isLoading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    @NonNull
    public String getLocationName() {
        if (mWeather == null)
            return "";
        return mWeather.getName() + ", " + mWeather.getSys().getCountry();
    }

    @Bindable
    @NonNull
    public String getTemp() {
        if (mWeather == null)
            return "";
        return Math.round(mWeather.getMain().getTemp()) + "°C";
    }

    @Bindable
    @NonNull
    public String getDescription() {
        if (mWeather == null)
            return "";
        String description = mWeather.getWeather().getDescription();
        return description.substring(0,1).toUpperCase() + description.substring(1);
    }

    @Bindable
    @NonNull
    public String getIconId() {
        if (mWeather == null)
            return "";
        return mWeather.getWeather().getIconId();
    }

    @Bindable
    @NonNull
    public String getPressure() {
        if (mWeather == null)
            return "";
        String formattedPressure = new DecimalFormat("#0.00")
                .format(mWeather.getMain().getPressure());
        return formattedPressure + " hPa";
    }

    @Bindable
    @NonNull
    public String getHumidity() {
        if (mWeather == null)
            return "";
        return mWeather.getMain().getHumidity() + "%";
    }

    @Bindable
    @NonNull
    public String getWind() {
        if (mWeather == null)
            return "";
        DecimalFormat doubleFormat = new DecimalFormat("#0.00");
        return doubleFormat.format(mWeather.getWind().getSpeed()) + " km/h, " +
                doubleFormat.format(mWeather.getWind().getDegree()) + " degrees";
    }

    @Bindable
    @NonNull
    public String getSunrise() {
        if (mWeather == null)
            return "";

        return DateFormat.format("HH:mm", mWeather.getSys().getSunrise()).toString();
    }

    @Bindable
    @NonNull
    public String getSunset() {
        if (mWeather == null)
            return "";

        return DateFormat.format("HH:mm", mWeather.getSys().getSunset()).toString();
    }

}
