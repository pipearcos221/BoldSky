package co.com.pipearcos221.boldsky.feature.detail.presentation.state

import co.com.pipearcos221.boldsky.core.ui.model.DomainError
import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail

data class DetailState(
    val isLoading: Boolean = true,
    val weatherDetail: WeatherDetail? = null,
    val error: DomainError? = null
)
