package co.com.pipearcos221.boldsky.feature.detail.domain.repository

import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail

interface WeatherDetailRepository {
    suspend fun getWeatherDetail(query: String): Result<WeatherDetail>
}
