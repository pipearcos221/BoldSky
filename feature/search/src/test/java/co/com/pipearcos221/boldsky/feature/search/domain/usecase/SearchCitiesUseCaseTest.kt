package co.com.pipearcos221.boldsky.feature.search.domain.usecase

import co.com.pipearcos221.boldsky.feature.search.domain.model.City
import co.com.pipearcos221.boldsky.feature.search.domain.repository.CityRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class SearchCitiesUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var cityRepository: CityRepository

    private lateinit var searchCitiesUseCase: SearchCitiesUseCase

    @Test
    fun `invoke with blank query should return success with empty list`() = runTest {
        // Given
        searchCitiesUseCase = SearchCitiesUseCase(cityRepository)
        val blankQuery = "   "

        // When
        val result = searchCitiesUseCase(blankQuery)

        // Then
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull().orEmpty().isEmpty())
        coVerify(exactly = 0) { cityRepository.searchCities(any()) }
    }

    @Test
    fun `invoke with valid query should return success with city list`() = runTest {
        // Given
        searchCitiesUseCase = SearchCitiesUseCase(cityRepository)
        val query = "Pasto"
        val expectedCities = listOf(City(1, "Pasto", "Nariño", "Colombia", 1.2, -77.2))
        coEvery { cityRepository.searchCities(query) } returns Result.success(expectedCities)

        // When
        val result = searchCitiesUseCase(query)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedCities, result.getOrNull())
        coVerify(exactly = 1) { cityRepository.searchCities(query) }
    }

    @Test
    fun `invoke when repository fails should return failure`() = runTest {
        // Given
        searchCitiesUseCase = SearchCitiesUseCase(cityRepository)
        val query = "ErrorCity"
        val expectedException = Exception("Network Error")
        coEvery { cityRepository.searchCities(query) } returns Result.failure(expectedException)

        // When
        val result = searchCitiesUseCase(query)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        coVerify(exactly = 1) { cityRepository.searchCities(query) }
    }
}
