package co.com.pipearcos221.boldsky.feature.detail.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayDto(
    val date: String,
    val day: DayDto,
    val astro: AstroDto,
    val hour: List<HourDto>? = null
)
