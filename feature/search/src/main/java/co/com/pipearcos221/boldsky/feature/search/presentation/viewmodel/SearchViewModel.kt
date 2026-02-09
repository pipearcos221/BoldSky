package co.com.pipearcos221.boldsky.feature.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.pipearcos221.boldsky.core.network.util.NetworkError
import co.com.pipearcos221.boldsky.feature.search.domain.usecase.SearchCitiesUseCase
import co.com.pipearcos221.boldsky.feature.search.presentation.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")
    private var searchJob: Job? = null

    init {
        observeSearchQuery()
    }

    fun onQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
        searchQuery.value = query
    }

    private fun observeSearchQuery() = searchQuery
        .debounce(DEBOUNCE_TIMEOUT_MS)
        .distinctUntilChanged()
        .onEach { query ->
            if (query.isNotBlank()) {
                searchCities(query)
            } else {
                _uiState.update { it.copy(cities = emptyList(), error = null) }
            }
        }
        .launchIn(viewModelScope)

    private fun searchCities(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            searchCitiesUseCase(query).fold(
                onSuccess = { cities ->
                    _uiState.update { it.copy(isLoading = false, cities = cities) }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = mapError(error),
                            cities = emptyList()
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

    private companion object {
        private const val DEBOUNCE_TIMEOUT_MS = 500L
    }
}
