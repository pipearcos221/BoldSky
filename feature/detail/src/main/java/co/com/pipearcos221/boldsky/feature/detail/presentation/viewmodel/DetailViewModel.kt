package co.com.pipearcos221.boldsky.feature.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.pipearcos221.boldsky.core.network.util.NetworkError
import co.com.pipearcos221.boldsky.feature.detail.domain.usecase.GetWeatherDetailUseCase
import co.com.pipearcos221.boldsky.feature.detail.presentation.state.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getWeatherDetailUseCase: GetWeatherDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailState())
    val uiState: StateFlow<DetailState> = _uiState.asStateFlow()

    init {
        val city = savedStateHandle.get<String>("city")
        if (city != null) {
            fetchWeatherDetail(city)
        }
    }

    private fun fetchWeatherDetail(city: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            val result = getWeatherDetailUseCase(city)
            
            result.fold(
                onSuccess = { weatherDetail ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            weatherDetail = weatherDetail,
                            error = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = mapError(error)
                        )
                    }
                }
            )
        }
    }

    private fun mapError(error: Throwable): String {
        return when (error) {
            is NetworkError.ConnectivityError -> "No internet connection"
            is NetworkError.HttpError -> "Server error: ${error.code}"
            else -> "An unknown error occurred"
        }
    }
}
