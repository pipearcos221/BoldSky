package co.com.pipearcos221.boldsky.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.com.pipearcos221.boldsky.feature.detail.navigation.DETAIL_ROUTE
import co.com.pipearcos221.boldsky.feature.detail.navigation.detailScreen
import co.com.pipearcos221.boldsky.feature.search.navigation.SEARCH_ROUTE
import co.com.pipearcos221.boldsky.feature.search.navigation.searchScreen
import co.com.pipearcos221.boldsky.splash.SPLASH_ROUTE
import co.com.pipearcos221.boldsky.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SPLASH_ROUTE,
        modifier = modifier,
    ) {
        composable(SPLASH_ROUTE) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(SEARCH_ROUTE) {
                        popUpTo(SPLASH_ROUTE) { inclusive = true }
                    }
                }
            )
        }

        searchScreen(
            onItemClick = { cityName -> navController.navigate("$DETAIL_ROUTE/$cityName") }
        )

        detailScreen()
    }
}
