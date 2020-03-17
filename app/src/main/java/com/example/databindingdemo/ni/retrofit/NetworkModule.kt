package com.example.databindingdemo.ni.retrofit

import com.example.databindingdemo.BuildConfig
import com.example.databindingdemo.base.BaseApplication
import com.example.databindingdemo.util.constants.Constants
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by Darshan Patel
 * Usage: functions to create network requirements such as okHttpClient
 * How to call: just call [createNetworkClient] in AppInjector
 *
 */


private val sLogLevel =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

val HEADER_CACHE_CONTROL = "Cache-Control"
val HEADER_PRAGMA = "Pragma"
// cache size mention here
private val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB

private fun cache(): Cache {
    return Cache(File(BaseApplication.instance?.cacheDir, Constants.retrofitCacheFile), cacheSize)
}

private fun getLogInterceptor() = HttpLoggingInterceptor().apply { level = sLogLevel }
        .setLevel(HttpLoggingInterceptor.Level.BODY)

/**
 *
 * This interceptor will be called ONLY if the network is available
 * @return
 */

private fun networkInterceptor(): Interceptor {
    return Interceptor { chain ->

        val response = chain.proceed(chain.request())

        val cacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.MINUTES)
                .build()

        response.newBuilder()
                /*.removeHeader(HEADER_PRAGMA)*/
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
    }
}

/**
 *
 * This interceptor will be called if network is not available
 * @return cache data if available
 */

private fun offlineInterceptor(): Interceptor {
    return Interceptor { chain ->
        var request = chain.request()

        // prevent caching when network is on. For that we use the "networkInterceptor"
        if (!BaseApplication.hasNetwork()) {
            val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()

            request = request.newBuilder()
                    /*.removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)*/
                    .cacheControl(cacheControl)
                    .build()
        }

        chain.proceed(request)
    }
}

fun createNetworkClient() =
        retrofitClient(okHttpClient())

private fun okHttpClient() = OkHttpClient.Builder()
        .cache(cache())
        .addInterceptor(getLogInterceptor()) // used if network off
        .addNetworkInterceptor(networkInterceptor()) // only used when network is on
        .addInterceptor(offlineInterceptor())
        .apply { setTimeOutToOkHttpClient(this) }
        .addInterceptor(headersInterceptor())
        .build()


private fun retrofitClient(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                // for cache response
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


fun headersInterceptor() = Interceptor { chain ->
    chain.proceed(
            chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
    )
}

private fun setTimeOutToOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) =
        okHttpClientBuilder.apply {
            readTimeout(30L, TimeUnit.SECONDS)
            connectTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(30L, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }