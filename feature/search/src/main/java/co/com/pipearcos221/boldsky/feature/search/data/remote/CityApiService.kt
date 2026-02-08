package co.com.pipearcos221.boldsky.feature.search.data.remote

import co.com.pipearcos221.boldsky.feature.search.data.dto.CityResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApiService {

    @GET("search.json")
    suspend fun searchCities(@Query("q") query: String): List<CityResponseDto>
}
