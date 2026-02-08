package co.com.pipearcos221.boldsky.feature.detail.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayDto(
    @SerialName("maxtemp_c")
    val maxTempC: Double,
    @SerialName("mintemp_c")
    val minTempC: Double,
    @SerialName("avgtemp_c")
    val avgTempC: Double,
    val condition: ConditionDto
)
