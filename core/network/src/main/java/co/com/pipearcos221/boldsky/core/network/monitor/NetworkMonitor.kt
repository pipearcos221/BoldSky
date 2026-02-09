package co.com.pipearcos221.boldsky.core.network.monitor

import kotlinx.coroutines.flow.Flow

/**
 * A utility for observing network connectivity status.
 */
interface NetworkMonitor {
    /**
     * A [Flow] that emits `true` if the network is connected and `false` otherwise.
     */
    val isOnline: Flow<Boolean>
}
