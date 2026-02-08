package co.com.pipearcos221.boldsky.feature.search.data.mapper

import co.com.pipearcos221.boldsky.feature.search.data.dto.CityResponseDto
import co.com.pipearcos221.boldsky.feature.search.domain.model.City

fun CityResponseDto.toCity(): City =
    City(
        id = id,
        name = name,
        region = region,
        country = country,
        lat = lat,
        lon = lon
    )
