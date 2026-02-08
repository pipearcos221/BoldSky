package co.com.pipearcos221.boldsky.core.network.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Executes a safe API call, handling common network exceptions and wrapping the result.
 *
 * @param T The type of the successful response data.
 * @param apiCall The suspend function representing the API call.
 * @return A [Result] which is either a [Result.success] with the data of type [T] or a [Result.failure] with a [NetworkError].
 */
suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.success(apiCall())
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(NetworkError.ConnectivityError(e))
                is HttpException -> Result.failure(
                    NetworkError.HttpError(
                        code = e.code(),
                        errorMessage = e.message(),
                        cause = e
                    )
                )

                else -> Result.failure(NetworkError.UnknownError(e))
            }
        }
    }
}
