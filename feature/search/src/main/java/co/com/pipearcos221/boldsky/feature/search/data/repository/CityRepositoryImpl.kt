package co.com.pipearcos221.boldsky.feature.search.data.repository

import co.com.pipearcos221.boldsky.feature.search.data.dto.toCity
import co.com.pipearcos221.boldsky.feature.search.data.remote.CityApiService
import co.com.pipearcos221.boldsky.feature.search.domain.model.City
import co.com.pipearcos221.boldsky.feature.search.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val apiService: CityApiService
) : CityRepository {

    override suspend fun searchCities(query: String): List<City> {
        return apiService.searchCities(query).map { it.toCity() }
    }
}
