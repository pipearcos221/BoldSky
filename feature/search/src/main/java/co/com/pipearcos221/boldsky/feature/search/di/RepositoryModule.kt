package co.com.pipearcos221.boldsky.feature.search.di

import co.com.pipearcos221.boldsky.feature.search.data.repository.CityRepositoryImpl
import co.com.pipearcos221.boldsky.feature.search.domain.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCityRepository(cityRepositoryImpl: CityRepositoryImpl): CityRepository =
        cityRepositoryImpl
}
