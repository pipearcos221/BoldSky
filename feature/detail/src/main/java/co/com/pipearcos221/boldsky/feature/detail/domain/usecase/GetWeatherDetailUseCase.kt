package co.com.pipearcos221.boldsky.feature.detail.domain.usecase

import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail
import co.com.pipearcos221.boldsky.feature.detail.domain.repository.WeatherDetailRepository
import javax.inject.Inject

class GetWeatherDetailUseCase @Inject constructor(
    private val weatherDetailRepository: WeatherDetailRepository
) {
    suspend operator fun invoke(query: String): Result<WeatherDetail> =
        weatherDetailRepository.getWeatherDetail(query)
}
