package com.io.testappvtb.data.api

import androidx.viewbinding.BuildConfig
import com.io.testappvtb.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

private val sLogLevel =
    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

fun createNetworkClient() = retrofitClient(
    getUnsafeOkHttpClient()
)

private fun retrofitClient(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun getUnsafeOkHttpClient(): OkHttpClient {
    return try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                    return arrayOf()
                }
            }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        val trustManagerFactory: TrustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        val trustManagers: Array<TrustManager> =
            trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            "Unexpected default trust managers:" + trustManagers.contentToString()
        }

        val trustManager =
            trustManagers[0] as X509TrustManager

        OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustManager)
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
            .addInterceptor(getLogInterceptor()).apply {
                setTimeOutToOkHttpClient(this)
            }
            .build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

private fun getLogInterceptor() = HttpLoggingInterceptor().apply { level = sLogLevel }

private fun setTimeOutToOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) =
    okHttpClientBuilder.apply {
        readTimeout(30L, TimeUnit.SECONDS)
        connectTimeout(30L, TimeUnit.SECONDS)
        writeTimeout(30L, TimeUnit.SECONDS)
    }