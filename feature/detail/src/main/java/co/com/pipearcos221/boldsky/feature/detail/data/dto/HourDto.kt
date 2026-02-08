package co.com.pipearcos221.boldsky.feature.detail.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourDto(
    @SerialName("time_epoch")
    val timeEpoch: Long,
    val time: String,
    @SerialName("temp_c")
    val tempC: Double,
    val condition: ConditionDto
)
