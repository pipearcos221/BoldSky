package co.com.pipearcos221.boldsky.feature.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.pipearcos221.boldsky.core.network.monitor.NetworkMonitor
import co.com.pipearcos221.boldsky.core.network.util.NetworkError
import co.com.pipearcos221.boldsky.core.ui.model.DomainError
import co.com.pipearcos221.boldsky.feature.detail.domain.usecase.GetWeatherDetailUseCase
import co.com.pipearcos221.boldsky.feature.detail.navigation.ARG_CITY_NAME
import co.com.pipearcos221.boldsky.feature.detail.presentation.state.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getWeatherDetailUseCase: GetWeatherDetailUseCase,
    private val networkMonitor: NetworkMonitor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailState())
    val uiState: StateFlow<DetailState> = _uiState.asStateFlow()

    private val cityName: String = savedStateHandle[ARG_CITY_NAME] ?: ""

    init {
        observeNetworkStatus()
        getWeatherDetail()
    }

    fun onRetry() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        getWeatherDetail()
    }

    private fun observeNetworkStatus() {
        networkMonitor.isOnline
            .onEach { isOnline ->
                if (!isOnline) {
                    _uiState.value = _uiState.value.copy(error = DomainError.Network, isLoading = false)
                } else {
                    if (_uiState.value.error is DomainError.Network) {
                        onRetry()
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getWeatherDetail() {
        viewModelScope.launch {
            getWeatherDetailUseCase(query = cityName)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(weatherDetail = it, isLoading = false, error = null)
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
