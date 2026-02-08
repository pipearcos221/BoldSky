package co.com.pipearcos221.boldsky.feature.search.data.repository

import co.com.pipearcos221.boldsky.core.network.util.safeApiCall
import co.com.pipearcos221.boldsky.feature.search.data.mapper.toCity
import co.com.pipearcos221.boldsky.feature.search.data.remote.CityApiService
import co.com.pipearcos221.boldsky.feature.search.domain.model.City
import co.com.pipearcos221.boldsky.feature.search.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val apiService: CityApiService
) : CityRepository {

    override suspend fun searchCities(query: String): Result<List<City>> {
        return safeApiCall { apiService.searchCities(query).map { it.toCity() } }
    }
}
