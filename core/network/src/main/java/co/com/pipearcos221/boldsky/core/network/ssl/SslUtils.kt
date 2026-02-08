package co.com.pipearcos221.boldsky.core.network.ssl

import android.content.Context
import co.com.pipearcos221.boldsky.core.network.R
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object SslUtils {

    private const val SSL_PROTOCOL = "TLSv1.2"
    private const val CERTIFICATE_TYPE = "X.509"
    private const val CERTIFICATE_ALIAS = "ca"
    private const val TRUST_MANAGER_INDEX = 0

    fun getSslContext(trustManager: X509TrustManager): SSLContext {
        return SSLContext.getInstance(SSL_PROTOCOL).apply {
            init(null, arrayOf<TrustManager>(trustManager), null)
        }
    }

    fun getTrustManager(context: Context): X509TrustManager {
        val certificateFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE)
        val inputStream: InputStream = context.resources.openRawResource(R.raw.isrg_root_x1)
        
        val certificate = inputStream.use { certificateFactory.generateCertificate(it) }

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            load(null, null)
            setCertificateEntry(CERTIFICATE_ALIAS, certificate)
        }

        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
            init(keyStore)
        }

        return trustManagerFactory.trustManagers[TRUST_MANAGER_INDEX] as X509TrustManager
    }
}
