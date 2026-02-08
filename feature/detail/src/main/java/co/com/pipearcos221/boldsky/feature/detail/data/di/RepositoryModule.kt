package co.com.pipearcos221.boldsky.feature.detail.data.di

import co.com.pipearcos221.boldsky.feature.detail.data.repository.WeatherDetailRepositoryImpl
import co.com.pipearcos221.boldsky.feature.detail.domain.repository.WeatherDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherDetailRepository(
        impl: WeatherDetailRepositoryImpl
    ): WeatherDetailRepository
}
