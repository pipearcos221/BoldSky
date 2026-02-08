package co.com.pipearcos221.boldsky.feature.detail.domain.model

data class DailyForecast(
    val date: String,
    val maxTempC: Double,
    val minTempC: Double,
    val conditionIconUrl: String
)
