package co.com.pipearcos221.boldsky.core.ui.model

/**
 * A structured representation of errors that can occur in the domain layer.
 */
sealed interface DomainError {
    /** An error related to network connectivity issues. */
    data object Network : DomainError

    /** An error indicating a problem with the server (e.g., 5xx responses). */
    data object Server : DomainError

    /** An unknown or unexpected error. */
    data object Unknown : DomainError
}
