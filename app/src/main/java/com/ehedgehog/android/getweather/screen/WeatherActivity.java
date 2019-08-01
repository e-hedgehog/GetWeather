package com.ehedgehog.android.getweather.screen;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;

import com.ehedgehog.android.getweather.AppDelegate;
import com.ehedgehog.android.getweather.R;
import com.ehedgehog.android.getweather.WeatherRepository;
import com.ehedgehog.android.getweather.databinding.ActivityWeatherBinding;
import com.google.android.gms.location.FusedLocationProviderClient;

import javax.inject.Inject;

public class WeatherActivity extends AppCompatActivity {

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    @Inject
    WeatherRepository mRepository;

    @Inject
    FusedLocationProviderClient mLocationProviderClient;

    private WeatherViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppDelegate.getAppComponent().injectWeatherActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mViewModel = new WeatherViewModel(mRepository, mLocationProviderClient);
        mViewModel.setupRepository();

        ActivityWeatherBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_weather);
        binding.setViewmodel(mViewModel);

        if (hasLocationPermission()) {
//            if (isOnline())
                mViewModel.loadWeather();
        } else
            ActivityCompat.requestPermissions(this,
                    LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {
//                    if (isOnline())
                        mViewModel.loadWeather();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean hasLocationPermission() {
        int result = ActivityCompat.checkSelfPermission(this, LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

}
