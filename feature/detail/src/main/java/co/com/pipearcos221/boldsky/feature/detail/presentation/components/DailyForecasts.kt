package co.com.pipearcos221.boldsky.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.com.pipearcos221.boldsky.core.ui.theme.AppDimens
import co.com.pipearcos221.boldsky.feature.detail.R
import co.com.pipearcos221.boldsky.feature.detail.domain.model.DailyForecast
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val DATE_FORMAT_API = "yyyy-MM-dd"
private const val DATE_FORMAT_DAY_ABBREVIATED = "EEE"
private const val FullWeight = 1f

@Composable
fun DailyForecasts(
    dailyForecasts: List<DailyForecast>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.three_day_forecast),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(AppDimens.SpacingMedium))
        Column(verticalArrangement = Arrangement.spacedBy(AppDimens.SpacingMedium)) {
            dailyForecasts.forEach { forecast ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(AppDimens.SpacingLarge),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = getDayOfWeek(forecast.date),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(FullWeight)
                        )
                        AsyncImage(
                            model = forecast.conditionIconUrl,
                            contentDescription = stringResource(id = R.string.three_day_forecast),
                            modifier = Modifier.size(AppDimens.IconSizeLarge)
                        )
                        Row(
                            modifier = Modifier.weight(FullWeight),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.temp_format, forecast.minTempC),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = stringResource(id = R.string.temperature_range),
                                modifier = Modifier
                                    .padding(horizontal = AppDimens.SpacingMedium)
                                    .size(AppDimens.IconSizeSmall)
                            )
                            Text(
                                text = stringResource(id = R.string.temp_format, forecast.maxTempC),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getDayOfWeek(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat(DATE_FORMAT_API, Locale.getDefault())
        val date = inputFormat.parse(dateString) ?: return dateString

        val today = Calendar.getInstance()
        val forecastDate = Calendar.getInstance().apply { time = date }

        if (today.get(Calendar.YEAR) == forecastDate.get(Calendar.YEAR) &&
            today.get(Calendar.DAY_OF_YEAR) == forecastDate.get(Calendar.DAY_OF_YEAR)) {
            "Today"
        } else {
            val outputFormat = SimpleDateFormat(DATE_FORMAT_DAY_ABBREVIATED, Locale.getDefault())
            outputFormat.format(date).replaceFirstChar { it.uppercase() }
        }
    } catch (_: Exception) {
        dateString
    }
}
