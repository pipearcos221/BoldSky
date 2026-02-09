package co.com.pipearcos221.boldsky.feature.detail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.com.pipearcos221.boldsky.feature.detail.domain.model.DailyForecast
import co.com.pipearcos221.boldsky.feature.detail.domain.model.HourlyForecast
import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail
import co.com.pipearcos221.boldsky.feature.detail.presentation.viewmodel.DetailViewModel
import coil.compose.AsyncImage

@Composable
fun DetailScreen(viewModel: DetailViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold {
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        LocationHeader(weatherDetail)
        Spacer(modifier = Modifier.height(24.dp))
        CurrentWeather(weatherDetail)
        Spacer(modifier = Modifier.height(24.dp))
        HourlyForecast(weatherDetail.hourlyForecasts)
        Spacer(modifier = Modifier.height(24.dp))
        DailyForecast(weatherDetail.dailyForecasts)
    }
}

@Composable
private fun LocationHeader(weatherDetail: WeatherDetail) {
    Column {
        Text(
            text = "${weatherDetail.locationName}, ${weatherDetail.locationRegion}",
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = weatherDetail.locationCountry,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun CurrentWeather(weatherDetail: WeatherDetail) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = weatherDetail.currentConditionIconUrl,
                    contentDescription = "Current weather icon",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${weatherDetail.currentTempC}°C",
                    style = MaterialTheme.typography.displayMedium
                )
            }
            Text(text = weatherDetail.currentConditionText, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem(icon = Icons.Default.Thermostat, label = "Feels like", value = "${weatherDetail.feelsLikeTempC}°C")
            DetailItem(icon = Icons.Default.WindPower, label = "Wind", value = "${weatherDetail.windSpeedKph} kph")
            DetailItem(icon = Icons.Default.WaterDrop, label = "Humidity", value = "${weatherDetail.humidity}%")
            DetailItem(icon = Icons.Default.Visibility, label = "Visibility", value = "${weatherDetail.visibilityKm} km")
        }
    }
}

@Composable
private fun DetailItem(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$label: $value", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun HourlyForecast(hourlyForecasts: List<HourlyForecast>) {
    Column {
        Text(text = "Today's Forecast", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(hourlyForecasts) { forecast ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = forecast.time, style = MaterialTheme.typography.bodySmall)
                    AsyncImage(model = forecast.conditionIconUrl, contentDescription = "Hourly forecast icon", modifier = Modifier.size(40.dp))
                    Text(text = "${forecast.tempC}°C", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun DailyForecast(dailyForecasts: List<DailyForecast>) {
    Column {
        Text(text = "3-Day Forecast", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        dailyForecasts.forEach { forecast ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = forecast.date, style = MaterialTheme.typography.bodyLarge)
                    Text(text = "${forecast.maxTempC}°C / ${forecast.minTempC}°C", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
