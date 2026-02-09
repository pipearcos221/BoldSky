package co.com.pipearcos221.boldsky.feature.detail.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.com.pipearcos221.boldsky.feature.detail.domain.model.WeatherDetail

@Composable
fun LocationHeader(
    weatherDetail: WeatherDetail,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = weatherDetail.locationName, style = MaterialTheme.typography.headlineLarge)
        Text(text = "${weatherDetail.locationRegion}, ${weatherDetail.locationCountry}", style = MaterialTheme.typography.bodyLarge)
    }
}
