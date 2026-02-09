package co.com.pipearcos221.boldsky.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
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

@Composable
fun SunTimesCard(sunrise: String, sunset: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(AppDimens.SpacingLarge)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = stringResource(R.string.sunrise),
                    modifier = Modifier.size(AppDimens.IconSizeNormal),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(AppDimens.SpacingMedium))
                Column {
                    Text(text = stringResource(R.string.sunrise), style = MaterialTheme.typography.labelMedium)
                    Text(text = sunrise, style = MaterialTheme.typography.bodyLarge)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.NightsStay,
                    contentDescription = stringResource(R.string.sunset),
                    modifier = Modifier.size(AppDimens.IconSizeNormal),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(AppDimens.SpacingMedium))
                Column {
                    Text(text = stringResource(R.string.sunset), style = MaterialTheme.typography.labelMedium)
                    Text(text = sunset, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
