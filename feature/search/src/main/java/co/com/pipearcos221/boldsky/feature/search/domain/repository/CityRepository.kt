package co.com.pipearcos221.boldsky.feature.search.domain.repository

import co.com.pipearcos221.boldsky.feature.search.domain.model.City

interface CityRepository {
    suspend fun searchCities(query: String): List<City>
}
