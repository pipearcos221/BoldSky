package co.com.pipearcos221.boldsky.feature.detail.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AstroDto(
    val sunrise: String,
    val sunset: String
)
