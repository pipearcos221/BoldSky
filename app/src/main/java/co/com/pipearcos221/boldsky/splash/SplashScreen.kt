package co.com.pipearcos221.boldsky.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.com.pipearcos221.boldsky.BuildConfig
import co.com.pipearcos221.boldsky.R
import co.com.pipearcos221.boldsky.core.ui.theme.PrimaryDark
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

const val SPLASH_ROUTE = "splash_route"
private val SplashAnimationSize = 250.dp
private const val ANIMATION_ITERATIONS = 1
private const val ANIMATION_END_PROGRESS = 1.0f

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.weather))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = ANIMATION_ITERATIONS
    )

    // Navigate when the animation is complete
    LaunchedEffect(progress) {
        if (progress == ANIMATION_END_PROGRESS) {
            onTimeout()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PrimaryDark.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.background
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(SplashAnimationSize)
        )

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.app_tagline),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(id = R.string.app_version_format, BuildConfig.VERSION_NAME),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}
