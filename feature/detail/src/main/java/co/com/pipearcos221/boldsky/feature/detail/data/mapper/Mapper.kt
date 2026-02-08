package co.com.pipearcos221.boldsky.feature.detail.data.mapper

import co.com.pipearcos221.boldsky.feature.detail.data.dto.WeatherDetailResponseDto
import co.com.pipearcos221.boldsky.feature.detail.domain.model.DailyForecast
import co.com.pipearcos221.boldsky.feature.detail.domain.model.HourlyForecast
import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail
import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_FORMAT_API = "yyyy-MM-dd HH:mm"
private const val DATE_FORMAT_HOUR = "HH:mm"
private const val SCHEME_HTTPS = "https"
private const val URL_SCHEME_PREFIX = "//"

fun WeatherDetailResponseDto.toWeatherDetail(): WeatherDetail {
    val today = this.forecast.forecastDay.firstOrNull()
    val hourlyForecasts = today?.hour?.map {
        HourlyForecast(
            time = it.time.toHour(),
            tempC = it.tempC,
            conditionIconUrl = it.condition.icon.toHttps()
        )
    } ?: emptyList()

    return WeatherDetail(
        locationName = this.location.name,
        locationRegion = this.location.region,
        locationCountry = this.location.country,
        currentTempC = this.current.tempC,
        feelsLikeTempC = this.current.feelsLikeC,
        currentConditionText = this.current.condition.text,
        currentConditionIconUrl = this.current.condition.icon.toHttps(),
        windSpeedKph = this.current.windKph,
        humidity = this.current.humidity,
        visibilityKm = this.current.visibilityKm,
        dailyForecasts = this.forecast.forecastDay.map {
            DailyForecast(
                date = it.date,
                maxTempC = it.day.maxTempC,
                minTempC = it.day.minTempC,
                conditionIconUrl = it.day.condition.icon.toHttps()
            )
        },
        hourlyForecasts = hourlyForecasts
    )
}

private fun String.toHour(): String {
    val parser = SimpleDateFormat(DATE_FORMAT_API, Locale.getDefault())
    val formatter = SimpleDateFormat(DATE_FORMAT_HOUR, Locale.getDefault())
    return parser.parse(this)?.let { formatter.format(it) } ?: this
}

private fun String.toHttps(): String {
    return if (this.startsWith(URL_SCHEME_PREFIX)) "$SCHEME_HTTPS:$this" else this
}
