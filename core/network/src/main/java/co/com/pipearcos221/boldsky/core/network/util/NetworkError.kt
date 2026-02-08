package co.com.pipearcos221.boldsky.core.network.util

import java.io.IOException

/**
 * A sealed class representing all possible errors that can occur during a network call.
 */
sealed class NetworkError(cause: Throwable) : Exception(cause) {

    /**
     * Represents a network connectivity issue (e.g., no internet).
     */
    class ConnectivityError(cause: IOException) : NetworkError(cause)

    /**
     * Represents a server-side error, identified by an HTTP status code.
     * @param code The HTTP status code.
     * @param errorMessage The error message from the server.
     */
    data class HttpError(
        val code: Int,
        val errorMessage: String?,
        override val cause: Throwable
    ) : NetworkError(cause)

    /**
     * Represents an unknown or unexpected error during the API call.
     */
    class UnknownError(cause: Throwable) : NetworkError(cause)
}
