package co.com.pipearcos221.boldsky.core.network.di

import android.content.Context
import android.os.Build
import co.com.pipearcos221.boldsky.core.network.BuildConfig
import co.com.pipearcos221.boldsky.core.network.monitor.NetworkMonitor
import co.com.pipearcos221.boldsky.core.network.monitor.NetworkMonitorImpl
import co.com.pipearcos221.boldsky.core.network.ssl.SslUtils
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CONTENT_TYPE_APPLICATION_JSON = "application/json"
    private const val API_KEY_QUERY_PARAM = "key"

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val newUrl = original.url.newBuilder()
                .addQueryParameter(API_KEY_QUERY_PARAM, BuildConfig.API_KEY)
                .build()

            val requestBuilder = original.newBuilder().url(newUrl)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })

        applyLegacySslConfiguration(builder, context)

        return builder.build()
    }

    private fun applyLegacySslConfiguration(builder: OkHttpClient.Builder, context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            val trustManager = SslUtils.getTrustManager(context)
            val sslContext = SslUtils.getSslContext(trustManager)
            builder.sslSocketFactory(sslContext.socketFactory, trustManager)
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val contenType = CONTENT_TYPE_APPLICATION_JSON.toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contenType))
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MonitorModule {

    @Binds
    @Singleton
    internal abstract fun bindsNetworkMonitor(
        impl: NetworkMonitorImpl
    ): NetworkMonitor
}
