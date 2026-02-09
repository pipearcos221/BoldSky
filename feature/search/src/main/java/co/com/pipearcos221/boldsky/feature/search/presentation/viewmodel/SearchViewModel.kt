package co.com.pipearcos221.boldsky.feature.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.pipearcos221.boldsky.core.network.monitor.NetworkMonitor
import co.com.pipearcos221.boldsky.core.network.util.NetworkError
import co.com.pipearcos221.boldsky.core.ui.model.DomainError
import co.com.pipearcos221.boldsky.feature.search.domain.usecase.SearchCitiesUseCase
import co.com.pipearcos221.boldsky.feature.search.presentation.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEBOUNCE_TIME = 500L

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        networkMonitor.isOnline
            .onEach { isOnline ->
                if (!isOnline) {
                    _uiState.value = _uiState.value.copy(error = DomainError.Network, isLoading = false)
                } else {
                    if (_uiState.value.error is DomainError.Network) {
                        _uiState.value = _uiState.value.copy(error = null)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_TIME)
            searchCities(query)
        }
    }

    fun onRetry() {
        searchCities(uiState.value.query)
    }

    private fun searchCities(query: String) {
        if (query.isBlank()) {
            _uiState.value = _uiState.value.copy(cities = emptyList(), isLoading = false, error = null)
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            searchCitiesUseCase(query)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(cities = it, isLoading = false)
                }
                .onFailure {
                    val domainError = (it as? NetworkError)?.toDomainError() ?: DomainError.Unknown
                    _uiState.value = _uiState.value.copy(error = domainError, isLoading = false)
                }
        }
    }

    private fun NetworkError.toDomainError(): DomainError {
        return when (this) {
            is NetworkError.ConnectivityError -> DomainError.Network
            is NetworkError.HttpError -> {
                when (this.code) {
                    in 500..599 -> DomainError.Server
                    else -> DomainError.Unknown
                }
            }
            is NetworkError.UnknownError -> DomainError.Unknown
        }
    }
}
