package com.ehedgehog.android.getweather.di;

import android.content.Context;

import com.ehedgehog.android.getweather.BuildConfig;
import com.ehedgehog.android.getweather.WeatherRepository;
import com.ehedgehog.android.getweather.network.ApiKeyInterceptor;
import com.ehedgehog.android.getweather.network.WeatherService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    private Context mContext;

    public DataModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    FusedLocationProviderClient provideLocationProvider(@NonNull Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(@NonNull WeatherService service,
                                               @NonNull Context context) {
        return new WeatherRepository(service, context);
    }

    @Provides
    @Singleton
    WeatherService provideWeatherService(@NonNull Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .addInterceptor(new ApiKeyInterceptor())
                .build();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }
}
