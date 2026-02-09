package co.com.pipearcos221.boldsky.feature.search.presentation.viewmodel

import co.com.pipearcos221.boldsky.core.network.monitor.NetworkMonitor
import co.com.pipearcos221.boldsky.core.network.util.NetworkError
import co.com.pipearcos221.boldsky.core.ui.model.DomainError
import co.com.pipearcos221.boldsky.feature.search.domain.model.City
import co.com.pipearcos221.boldsky.feature.search.domain.usecase.SearchCitiesUseCase
import co.com.pipearcos221.boldsky.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var searchCitiesUseCase: SearchCitiesUseCase

    @RelaxedMockK
    private lateinit var networkMonitor: NetworkMonitor

    private lateinit var viewModel: SearchViewModel

    private val networkState = MutableStateFlow(true)

    @Before
    fun setUp() {
        coEvery { networkMonitor.isOnline } returns networkState
        viewModel = SearchViewModel(searchCitiesUseCase, networkMonitor)
    }

    @Test
    fun `Given ViewModel is created, Then initial state is correct`() = runTest {
        // When
        val initialState = viewModel.uiState.value

        // Then
        assertEquals("", initialState.query)
        assertTrue(initialState.cities.isEmpty())
        assertFalse(initialState.isLoading)
        assertNull(initialState.error)
    }

    @Test
    fun `Given use case returns success, When query is changed, Then state is updated with cities`() = runTest {
        // Given
        val query = "London"
        val cities = listOf(City(1, "London", "Region", "Country", 0.0, 0.0))
        coEvery { searchCitiesUseCase(query) } returns Result.success(cities)

        // When
        viewModel.onQueryChanged(query)
        advanceTimeBy(600L) // Advance time beyond debounce

        // Then
        val state = viewModel.uiState.value
        assertEquals(query, state.query)
        assertFalse(state.isLoading)
        assertEquals(cities, state.cities)
        assertNull(state.error)
    }

    @Test
    fun `Given use case returns server error, When query is changed, Then state is updated with server error`() = runTest {
        // Given
        val query = "ErrorCity"
        val error = NetworkError.HttpError(500, "Server Error", Throwable())
        coEvery { searchCitiesUseCase(query) } returns Result.failure(error)

        // When
        viewModel.onQueryChanged(query)
        advanceTimeBy(600L)

        // Then
        val state = viewModel.uiState.value
        assertEquals(query, state.query)
        assertFalse(state.isLoading)
        assertEquals(DomainError.Server, state.error)
    }

    @Test
    fun `Given network is online, When connection is lost, Then state is updated with network error`() = runTest {
        // When
        networkState.value = false
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()

        // Then
        val state = viewModel.uiState.value
        assertEquals(DomainError.Network, state.error)
    }

    @Test
    fun `Given a previous successful search, When retry is called, Then search is re-triggered`() = runTest {
        // Given
        val query = "RetryCity"
        val cities = listOf(City(1, "RetryCity", "Region", "Country", 0.0, 0.0))
        coEvery { searchCitiesUseCase(query) } returns Result.success(cities)
        viewModel.onQueryChanged(query)
        advanceTimeBy(600L) // Initial search

        // When
        viewModel.onRetry()
        advanceTimeBy(600L) // Retry search debounce

        // Then
        val state = viewModel.uiState.value
        assertEquals(query, state.query)
        assertFalse(state.isLoading)
        assertEquals(cities, state.cities)
    }
}
