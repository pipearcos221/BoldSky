package co.com.pipearcos221.boldsky.feature.search.domain.model

data class City(
    val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double
)
