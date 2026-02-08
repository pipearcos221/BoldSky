package co.com.pipearcos221.boldsky.feature.detail.data.repository

import co.com.pipearcos221.boldsky.core.network.util.safeApiCall
import co.com.pipearcos221.boldsky.feature.detail.data.mapper.toWeatherDetail
import co.com.pipearcos221.boldsky.feature.detail.data.remote.WeatherDetailApiService
import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail
import co.com.pipearcos221.boldsky.feature.detail.domain.repository.WeatherDetailRepository
import javax.inject.Inject

class WeatherDetailRepositoryImpl @Inject constructor(
    private val weatherDetailApiService: WeatherDetailApiService
) : WeatherDetailRepository {

    override suspend fun getWeatherDetail(query: String, days: Int): Result<WeatherDetail> =
        safeApiCall { weatherDetailApiService.getWeatherDetail(query, days).toWeatherDetail() }
}
