package co.com.pipearcos221.boldsky.feature.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import co.com.pipearcos221.boldsky.feature.detail.presentation.DetailScreen

const val DETAIL_ROUTE = "detail"
const val ARG_CITY_NAME = "city"

fun NavGraphBuilder.detailScreen(onNavigateUp: () -> Unit) {
    composable(
        route = "$DETAIL_ROUTE/{$ARG_CITY_NAME}",
        arguments = listOf(navArgument(ARG_CITY_NAME) { type = NavType.StringType })
    ) {
        DetailScreen(onNavigateUp = onNavigateUp)
    }
}
