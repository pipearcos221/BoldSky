package co.com.pipearcos221.boldsky.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import co.com.pipearcos221.boldsky.core.ui.theme.AppDimens
import co.com.pipearcos221.boldsky.feature.detail.R
import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail

private const val FullWeight = 1f

@Composable
fun WeatherMetricsGrid(
    weatherDetail: WeatherDetail,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.SpacingMedium),
        modifier = modifier
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(AppDimens.SpacingMedium)) {
            MetricCard(
                icon = Icons.Default.Thermostat,
                label = stringResource(R.string.feels_like),
                value = stringResource(R.string.temp_format, weatherDetail.feelsLikeTempC),
                modifier = Modifier.weight(FullWeight)
            )
            MetricCard(
                icon = Icons.Default.WaterDrop,
                label = stringResource(R.string.humidity),
                value = stringResource(R.string.humidity_percentage, weatherDetail.humidity),
                modifier = Modifier.weight(FullWeight)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(AppDimens.SpacingMedium)) {
            MetricCard(
                icon = Icons.Default.WindPower,
                label = stringResource(R.string.wind),
                value = stringResource(R.string.wind_speed_kph, weatherDetail.windSpeedKph),
                modifier = Modifier.weight(FullWeight)
            )
            MetricCard(
                icon = Icons.Default.Visibility,
                label = stringResource(R.string.visibility),
                value = stringResource(R.string.visibility_km, weatherDetail.visibilityKm),
                modifier = Modifier.weight(FullWeight)
            )
        }
    }
}

@Composable
private fun MetricCard(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(AppDimens.SpacingLarge)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = Modifier.size(AppDimens.IconSizeMedium),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(AppDimens.SpacingMedium))
                Text(text = label, style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.height(AppDimens.SpacingMedium))
            Text(text = value, style = MaterialTheme.typography.headlineSmall)
        }
    }
}
