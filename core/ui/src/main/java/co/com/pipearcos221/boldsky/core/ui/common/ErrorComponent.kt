package co.com.pipearcos221.boldsky.core.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import co.com.pipearcos221.boldsky.core.ui.R
import co.com.pipearcos221.boldsky.core.ui.model.DomainError
import co.com.pipearcos221.boldsky.core.ui.theme.AppAlphas
import co.com.pipearcos221.boldsky.core.ui.theme.AppDimens

@Composable
fun ErrorComponent(
    error: DomainError,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val errorState = rememberErrorState(error = error)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = errorState.icon,
            contentDescription = null,
            modifier = Modifier.size(AppDimens.IconSizeXXXLarge),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = AppAlphas.Alpha50)
        )
        Spacer(modifier = Modifier.height(AppDimens.SpacingLarge))
        Text(
            text = stringResource(id = errorState.messageRes),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(AppDimens.SpacingXLarge))
        Button(onClick = onRetry) {
            Text(text = stringResource(id = R.string.action_retry))
        }
    }
}

private data class ErrorState(
    val icon: ImageVector,
    val messageRes: Int
)

@Composable
private fun rememberErrorState(error: DomainError): ErrorState {
    return when (error) {
        DomainError.Network -> ErrorState(
            icon = Icons.Default.WifiOff,
            messageRes = R.string.error_network
        )
        DomainError.Server -> ErrorState(
            icon = Icons.Default.CloudOff,
            messageRes = R.string.error_server
        )
        DomainError.Unknown -> ErrorState(
            icon = Icons.Default.ReportProblem,
            messageRes = R.string.error_unknown
        )
    }
}
