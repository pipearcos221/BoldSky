package co.com.pipearcos221.boldsky.feature.search.presentation

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import co.com.pipearcos221.boldsky.core.ui.theme.AppDimens
import co.com.pipearcos221.boldsky.feature.search.R
import co.com.pipearcos221.boldsky.feature.search.domain.model.City
import co.com.pipearcos221.boldsky.feature.search.presentation.state.SearchState
import co.com.pipearcos221.boldsky.feature.search.presentation.viewmodel.SearchViewModel

private const val LandscapeSearchBarWidth = 0.6f

@Composable
fun SearchScreen(
    onItemClick: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    SearchScreenContent(uiState = uiState, onItemClick = onItemClick, onQueryChanged = viewModel::onQueryChanged)
}

@Composable
fun SearchScreenContent(
    uiState: SearchState,
    onItemClick: (String) -> Unit,
    onQueryChanged: (String) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimens.SpacingLarge),
        horizontalAlignment = if (isLandscape) Alignment.CenterHorizontally else Alignment.Start
    ) {
        val searchBarModifier = if (isLandscape) {
            Modifier.fillMaxWidth(LandscapeSearchBarWidth)
        } else {
            Modifier.fillMaxWidth()
        }

        SearchBar(
            query = uiState.query,
            onQueryChanged = onQueryChanged,
            modifier = searchBarModifier
        )

        Spacer(modifier = Modifier.height(AppDimens.SpacingLarge))

        SearchContent(
            uiState = uiState,
            onItemClick = onItemClick
        )
    }
}

@Composable
private fun SearchBar(query: String, onQueryChanged: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        label = { Text(stringResource(id = R.string.search_location_label)) },
        modifier = modifier,
        singleLine = true
    )
}

@Composable
private fun SearchContent(uiState: SearchState, onItemClick: (String) -> Unit) {
    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        uiState.cities.isEmpty() && uiState.query.isNotBlank() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(id = R.string.no_results_found))
            }
        }

        else -> {
            CityList(cities = uiState.cities, onItemClick = onItemClick)
        }
    }
}

@Composable
private fun CityList(cities: List<City>, onItemClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppDimens.SpacingMedium)
    ) {
        items(cities) { city ->
            val locationText = "${city.name}, ${city.region}, ${city.country}"
            Text(
                text = locationText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(city.name) }
                    .padding(vertical = AppDimens.SpacingLarge)
            )
        }
    }
}
