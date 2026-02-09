package co.com.pipearcos221.boldsky.feature.search.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import co.com.pipearcos221.boldsky.core.ui.theme.AppAlphas
import co.com.pipearcos221.boldsky.core.ui.theme.AppDimens
import co.com.pipearcos221.boldsky.core.ui.theme.PrimaryDark
import co.com.pipearcos221.boldsky.feature.search.R
import co.com.pipearcos221.boldsky.feature.search.domain.model.City
import co.com.pipearcos221.boldsky.feature.search.presentation.state.SearchState
import co.com.pipearcos221.boldsky.feature.search.presentation.viewmodel.SearchViewModel

private const val LandscapeSearchBarWidth = 0.6f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onItemClick: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PrimaryDark.copy(alpha = AppAlphas.Alpha30),
                        MaterialTheme.colorScheme.background
                    )
                )
            ),
        topBar = {
            TopAppBar(
                title = { Text("BoldSky", style = MaterialTheme.typography.titleLarge) },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = MaterialTheme.colorScheme.background.copy(alpha = AppAlphas.Alpha90)
                )
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        SearchScreenContent(
            uiState = uiState,
            onItemClick = onItemClick,
            onQueryChanged = viewModel::onQueryChanged,
            modifier = Modifier.padding(innerPadding)
        )
    }

}

@Composable
private fun SearchScreenContent(
    uiState: SearchState,
    onItemClick: (String) -> Unit,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(modifier = modifier.fillMaxSize()) {
        val searchBarModifier = if (isLandscape) {
            Modifier
                .fillMaxWidth(LandscapeSearchBarWidth)
                .align(Alignment.CenterHorizontally)
        } else {
            Modifier.fillMaxWidth()
        }

        SearchBar(
            query = uiState.query,
            onQueryChanged = onQueryChanged,
            modifier = searchBarModifier.padding(
                start = AppDimens.SpacingLarge,
                end = AppDimens.SpacingLarge,
                top = AppDimens.SpacingLarge,
                bottom = AppDimens.SpacingMedium
            )
        )

        if (uiState.cities.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppDimens.SpacingLarge),
                contentAlignment = Alignment.Center
            ) {
                when {
                    uiState.isLoading -> CircularProgressIndicator()
                    uiState.error != null -> Text(
                        text = uiState.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    uiState.query.isBlank() -> Text(
                        text = stringResource(id = R.string.search_initial_message),
                        textAlign = TextAlign.Center
                    )
                    else -> Text(
                        text = stringResource(id = R.string.no_results_found),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            CityList(
                cities = uiState.cities, 
                onItemClick = onItemClick,
                modifier = Modifier.padding(horizontal = AppDimens.SpacingLarge)
            )
        }
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
private fun CityList(
    cities: List<City>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(AppDimens.SpacingMedium),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.SpacingMedium)
        ) {
            items(cities, key = { it.id }) { city ->
                CityCard(
                    city = city,
                    onItemClick = onItemClick
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(AppDimens.SpacingMedium)
        ) {
            items(cities, key = { it.id }) { city ->
                CityCard(
                    city = city,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
private fun CityCard(
    city: City,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(city.name) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = AppAlphas.Alpha50)
        )
    ) {
        Row(
            modifier = Modifier.padding(AppDimens.SpacingLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null, // Decorative
                modifier = Modifier.size(AppDimens.IconSizeLarge)
            )
            Spacer(modifier = Modifier.width(AppDimens.SpacingLarge))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = city.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${city.region}, ${city.country}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null, // Decorative
                modifier = Modifier.size(AppDimens.IconSizeMedium),
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}
