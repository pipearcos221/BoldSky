package co.com.pipearcos221.boldsky.feature.detail.domain.model

data class HourlyForecast(
    val time: String,
    val tempC: Double,
    val conditionIconUrl: String
)
