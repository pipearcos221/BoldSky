package co.com.pipearcos221.boldsky.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

const val SPLASH_ROUTE = "splash_route"
private const val SPLASH_TIMEOUT = 2000L

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Welcome to BoldSky!")
    }

    LaunchedEffect(Unit) {
        delay(SPLASH_TIMEOUT)
        onTimeout()
    }
}
