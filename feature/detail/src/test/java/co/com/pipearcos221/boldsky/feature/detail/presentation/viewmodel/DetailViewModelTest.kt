package co.com.pipearcos221.boldsky.feature.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import co.com.pipearcos221.boldsky.core.network.monitor.NetworkMonitor
import co.com.pipearcos221.boldsky.core.network.util.NetworkError
import co.com.pipearcos221.boldsky.core.ui.model.DomainError
import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail
import co.com.pipearcos221.boldsky.feature.detail.domain.usecase.GetWeatherDetailUseCase
import co.com.pipearcos221.boldsky.feature.detail.navigation.ARG_CITY_NAME
import co.com.pipearcos221.boldsky.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var getWeatherDetailUseCase: GetWeatherDetailUseCase

    @RelaxedMockK
    private lateinit var networkMonitor: NetworkMonitor

    private val networkState = MutableStateFlow(true)

    private val dummyWeatherDetail = WeatherDetail(
        locationName = "Test City",
        locationRegion = "Test Region",
        locationCountry = "Test Country",
        currentTempC = 25.0,
        feelsLikeTempC = 26.0,
        currentConditionText = "Sunny",
        currentConditionIconUrl = "",
        windSpeedKph = 10.0,
        humidity = 60,
        visibilityKm = 10.0,
        dewPointC = 15.0,
        sunrise = "06:00 AM",
        sunset = "06:00 PM",
        dailyForecasts = emptyList(),
        hourlyForecasts = emptyList()
    )

    @Before
    fun setUp() {
        coEvery { networkMonitor.isOnline } returns networkState
    }

    @Test
    fun `Given ViewModel is created, Then initial state is loading`() = runTest {
        // Given
        coEvery { getWeatherDetailUseCase(any()) } returns Result.success(dummyWeatherDetail)

        // When
        val viewModel = createViewModel()
        val initialState = viewModel.uiState.value

        // Then
        assertEquals(true, initialState.isLoading)
        assertNull(initialState.weatherDetail)
        assertNull(initialState.error)
    }

    @Test
    fun `Given use case returns success, When data is fetched, Then state is updated with weather data`() = runTest {
        // Given
        coEvery { getWeatherDetailUseCase(any()) } returns Result.success(dummyWeatherDetail)

        // When
        val viewModel = createViewModel()
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNotNull(state.weatherDetail)
        assertEquals("Test City", state.weatherDetail?.locationName)
        assertNull(state.error)
    }

    @Test
    fun `Given use case returns server error, When data is fetched, Then state is updated with server error`() = runTest {
        // Given
        val error = NetworkError.HttpError(500, "Server Error", Throwable())
        coEvery { getWeatherDetailUseCase(any()) } returns Result.failure(error)
        
        // When
        val viewModel = createViewModel()
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.weatherDetail)
        assertEquals(DomainError.Server, state.error)
    }

    @Test
    fun `Given network is online, When connection is lost, Then state is updated with network error`() = runTest {
        // Given
        coEvery { getWeatherDetailUseCase(any()) } returns Result.success(dummyWeatherDetail)
        val viewModel = createViewModel()
        advanceUntilIdle() // Let initial fetch complete

        // When
        networkState.value = false
        advanceUntilIdle() // Process network change

        // Then
        val state = viewModel.uiState.value
        assertEquals(DomainError.Network, state.error)
        assertFalse(state.isLoading)
    }

    private fun createViewModel(): DetailViewModel {
        val savedStateHandle = SavedStateHandle(mapOf(ARG_CITY_NAME to "Test City"))
        return DetailViewModel(getWeatherDetailUseCase, networkMonitor, savedStateHandle)
    }
}
