package co.com.pipearcos221.boldsky.feature.detail.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailResponseDto(
    val location: LocationDto,
    val current: CurrentDto,
    val forecast: ForecastDto
)
