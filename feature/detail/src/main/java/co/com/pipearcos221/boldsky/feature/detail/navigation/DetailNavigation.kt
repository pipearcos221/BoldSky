package co.com.pipearcos221.boldsky.feature.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import co.com.pipearcos221.boldsky.feature.detail.DetailScreen

const val DETAIL_ROUTE = "detail_route"
const val ARG_ITEM_ID = "itemId"

fun NavGraphBuilder.detailScreen() {
    composable(
        route = "$DETAIL_ROUTE/{$ARG_ITEM_ID}",
        arguments = listOf(navArgument(ARG_ITEM_ID) { type = NavType.StringType })
    ) { backStackEntry ->
        val itemId = backStackEntry.arguments?.getString(ARG_ITEM_ID)
        DetailScreen(itemId = itemId)
    }
}
