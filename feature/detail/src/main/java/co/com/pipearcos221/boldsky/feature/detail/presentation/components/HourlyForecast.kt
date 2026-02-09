package co.com.pipearcos221.boldsky.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.com.pipearcos221.boldsky.core.ui.theme.AppDimens
import co.com.pipearcos221.boldsky.feature.detail.R
import co.com.pipearcos221.boldsky.feature.detail.domain.model.HourlyForecast
import coil.compose.AsyncImage

@Composable
fun HourlyForecast(
    hourlyForecasts: List<HourlyForecast>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.todays_forecast),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(AppDimens.SpacingMedium))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(AppDimens.SpacingNormal)) {
            items(hourlyForecasts) { forecast ->
                Card(
                    modifier = Modifier.padding(vertical = AppDimens.SpacingSmall),
                    elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.SpacingSmall)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = AppDimens.SpacingLarge, vertical = AppDimens.SpacingMedium)
                    ) {
                        Text(text = forecast.time, style = MaterialTheme.typography.bodySmall)
                        AsyncImage(
                            model = forecast.conditionIconUrl,
                            contentDescription = stringResource(id = R.string.todays_forecast),
                            modifier = Modifier.size(AppDimens.IconSizeLarge)
                        )
                        Text(
                            text = stringResource(id = R.string.temp_format, forecast.tempC),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
