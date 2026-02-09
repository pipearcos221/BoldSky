package co.com.pipearcos221.boldsky.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.com.pipearcos221.boldsky.core.ui.theme.AppDimens
import co.com.pipearcos221.boldsky.feature.detail.R
import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail
import coil.compose.AsyncImage

@Composable
fun CurrentWeatherHeader(
    weatherDetail: WeatherDetail,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = weatherDetail.currentConditionIconUrl,
            contentDescription = stringResource(R.string.current_weather),
            modifier = Modifier.size(AppDimens.IconSizeXXXLarge)
        )
        Spacer(modifier = Modifier.width(AppDimens.SpacingLarge))
        Text(
            text = stringResource(R.string.temp_format, weatherDetail.currentTempC),
            style = MaterialTheme.typography.displayLarge
        )
    }
}
