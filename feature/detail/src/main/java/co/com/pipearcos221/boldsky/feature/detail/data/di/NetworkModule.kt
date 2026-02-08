package co.com.pipearcos221.boldsky.feature.detail.data.di

import co.com.pipearcos221.boldsky.feature.detail.data.remote.WeatherDetailApiService
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
    fun provideWeatherDetailApiService(retrofit: Retrofit): WeatherDetailApiService {
        return retrofit.create(WeatherDetailApiService::class.java)
    }
}
