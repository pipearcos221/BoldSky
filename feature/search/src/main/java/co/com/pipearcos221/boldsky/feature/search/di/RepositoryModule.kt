package co.com.pipearcos221.boldsky.feature.search.di

import co.com.pipearcos221.boldsky.feature.search.data.repository.CityRepositoryImpl
import co.com.pipearcos221.boldsky.feature.search.domain.repository.CityRepository
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
    abstract fun bindCityRepository(impl: CityRepositoryImpl): CityRepository
}
