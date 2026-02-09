package co.com.pipearcos221.boldsky.feature.search.presentation.state

import co.com.pipearcos221.boldsky.core.ui.model.DomainError
import co.com.pipearcos221.boldsky.feature.search.domain.model.City

data class SearchState(
    val query: String = "",
    val cities: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val error: DomainError? = null
)
