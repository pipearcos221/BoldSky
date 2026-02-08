package co.com.pipearcos221.boldsky.feature.search.di

import co.com.pipearcos221.boldsky.feature.search.data.remote.CityApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCityApiService(retrofitBuilder: Retrofit): CityApiService {
        return retrofitBuilder.create(CityApiService::class.java)
    }
}
