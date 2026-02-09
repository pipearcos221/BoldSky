package co.com.pipearcos221.boldsky.feature.detail.presentation.state

import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail

data class DetailState(
    val isLoading: Boolean = false,
    val weatherDetail: WeatherDetail? = null,
    val error: String? = null
)
