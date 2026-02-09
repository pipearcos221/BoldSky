package co.com.pipearcos221.boldsky.feature.detail.domain.model

data class WeatherDetail(
    val locationName: String,
    val locationRegion: String,
    val locationCountry: String,
    val currentTempC: Double,
    val feelsLikeTempC: Double,
    val currentConditionText: String,
    val currentConditionIconUrl: String,
    val windSpeedKph: Double,
    val humidity: Int,
    val visibilityKm: Double,
    val dewPointC: Double,
    val sunrise: String,
    val sunset: String,
    val dailyForecasts: List<DailyForecast>,
    val hourlyForecasts: List<HourlyForecast>
)
