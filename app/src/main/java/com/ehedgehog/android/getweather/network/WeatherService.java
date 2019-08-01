package com.ehedgehog.android.getweather.network;

import com.ehedgehog.android.getweather.model.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    Observable<WeatherResponse> getCurrentWeather(@Query("lat") double lat,
                                                  @Query("lon") double lon,
                                                  @Query("units") String units);

}
