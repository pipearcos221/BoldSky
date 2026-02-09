package co.com.pipearcos221.boldsky.feature.detail.domain.usecase

import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail
import co.com.pipearcos221.boldsky.feature.detail.domain.repository.WeatherDetailRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class GetWeatherDetailUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var weatherDetailRepository: WeatherDetailRepository

    private lateinit var getWeatherDetailUseCase: GetWeatherDetailUseCase

    @Test
    fun `invoke with valid query should return success with weather detail`() = runTest {
        // Given
        getWeatherDetailUseCase = GetWeatherDetailUseCase(weatherDetailRepository)
        val query = "Pasto"
        val fakeWeatherDetail = WeatherDetail(
            locationName = "Pasto",
            locationRegion = "Nariño",
            locationCountry = "Colombia",
            currentTempC = 15.0,
            feelsLikeTempC = 14.0,
            currentConditionText = "Partly cloudy",
            currentConditionIconUrl = "//cdn.weatherapi.com/weather/64x64/day/116.png",
            windSpeedKph = 10.0,
            humidity = 70,
            visibilityKm = 10.0,
            dewPointC = 12.0,
            sunrise = "06:00 AM",
            sunset = "06:00 PM",
            dailyForecasts = emptyList(),
            hourlyForecasts = emptyList()
        )
        coEvery { weatherDetailRepository.getWeatherDetail(query) } returns Result.success(fakeWeatherDetail)

        // When
        val result = getWeatherDetailUseCase(query)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(fakeWeatherDetail, result.getOrNull())
        coVerify(exactly = 1) { weatherDetailRepository.getWeatherDetail(query) }
    }

    @Test
    fun `invoke when repository fails should return failure`() = runTest {
        // Given
        getWeatherDetailUseCase = GetWeatherDetailUseCase(weatherDetailRepository)
        val query = "ErrorCity"
        val expectedException = Exception("Network Error")
        coEvery { weatherDetailRepository.getWeatherDetail(query) } returns Result.failure(expectedException)

        // When
        val result = getWeatherDetailUseCase(query)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        coVerify(exactly = 1) { weatherDetailRepository.getWeatherDetail(query) }
    }
}
