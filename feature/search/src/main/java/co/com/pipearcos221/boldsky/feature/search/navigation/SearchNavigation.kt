package co.com.pipearcos221.boldsky.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import co.com.pipearcos221.boldsky.feature.search.presentation.SearchScreen

const val SEARCH_ROUTE = "search_route"

fun NavGraphBuilder.searchScreen(onItemClick: (String) -> Unit) {
    composable(route = SEARCH_ROUTE) {
        SearchScreen(onItemClick = onItemClick)
    }
}
