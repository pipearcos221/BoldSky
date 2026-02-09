package co.com.pipearcos221.boldsky.feature.detail.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import co.com.pipearcos221.boldsky.core.ui.theme.AppDimens
import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail
import co.com.pipearcos221.boldsky.feature.detail.presentation.components.CurrentWeatherHeader
import co.com.pipearcos221.boldsky.feature.detail.presentation.components.DailyForecasts
import co.com.pipearcos221.boldsky.feature.detail.presentation.components.HourlyForecast
import co.com.pipearcos221.boldsky.feature.detail.presentation.components.LocationHeader
import co.com.pipearcos221.boldsky.feature.detail.presentation.components.SunTimesCard
import co.com.pipearcos221.boldsky.feature.detail.presentation.components.WeatherMetricsGrid
import co.com.pipearcos221.boldsky.feature.detail.presentation.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onNavigateUp: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.weatherDetail?.locationName ?: "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.weatherDetail != null -> {
                    WeatherDetailContent(weatherDetail = uiState.weatherDetail!!)
                }
            }
        }
    }
}

@Composable
private fun WeatherDetailContent(weatherDetail: WeatherDetail) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeWeatherDetailContent(weatherDetail)
    } else {
        PortraitWeatherDetailContent(weatherDetail)
    }
}

@Composable
private fun PortraitWeatherDetailContent(weatherDetail: WeatherDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimens.SpacingLarge)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppDimens.SpacingLarge)
    ) {
        LocationHeader(weatherDetail)
        CurrentWeatherHeader(weatherDetail)
        WeatherMetricsGrid(weatherDetail)
        SunTimesCard(sunrise = weatherDetail.sunrise, sunset = weatherDetail.sunset)
        HourlyForecast(weatherDetail.hourlyForecasts)
        DailyForecasts(weatherDetail.dailyForecasts)
    }
}

@Composable
private fun LandscapeWeatherDetailContent(weatherDetail: WeatherDetail) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimens.SpacingLarge),
        horizontalArrangement = Arrangement.spacedBy(AppDimens.SpacingLarge)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(AppDimens.SpacingLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocationHeader(weatherDetail)
            CurrentWeatherHeader(weatherDetail)
            HourlyForecast(weatherDetail.hourlyForecasts)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(AppDimens.SpacingLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherMetricsGrid(weatherDetail)
            SunTimesCard(sunrise = weatherDetail.sunrise, sunset = weatherDetail.sunset)
            DailyForecasts(weatherDetail.dailyForecasts)
        }
    }
}
