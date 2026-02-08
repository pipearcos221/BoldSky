package co.com.pipearcos221.boldsky.feature.detail.data.remote

import co.com.pipearcos221.boldsky.feature.detail.data.dto.WeatherDetailResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherDetailApiService {

    @GET("forecast.json")
    suspend fun getWeatherDetail(
        @Query("q") query: String,
        @Query("days") days: Int = 3
    ): WeatherDetailResponseDto
}
