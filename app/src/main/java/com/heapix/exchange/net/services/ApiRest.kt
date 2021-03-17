package com.heapix.exchange.net.services

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiRest {

    companion object {

        private const val BASE_URL: String =
            "https://v6.exchangerate-api.com/v6/94ddd269f21f0be7b7f9dcef/"
        private const val API_KEY: String = "api_key"

        //        private const val API_KEY_VALUE: String = "94ddd269f21f0be7b7f9dcef"
        private const val TIMEOUT: Long = 10

        internal fun getApi(): Retrofit {
            val client = getOkHttpClient()

            val builder = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(getGsonConverterFactory())
                .client(client)

            return builder.build()
        }

        private fun getGsonConverterFactory(): Converter.Factory {
            return GsonConverterFactory.create(GsonBuilder().setLenient().create())
        }

        private fun getOkHttpClient(): OkHttpClient {
            return OkHttpClient()
                .newBuilder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                    val originalHttpUrl = chain.request().url
                    val url = originalHttpUrl
                        .newBuilder()
//                        .addQueryParameter(
//                            API_KEY,
//                            API_KEY_VALUE
//                        )
                        .build()
                    request.url(url)
                    return@addInterceptor chain.proceed(request.build())
                }
                .addNetworkInterceptor(Interceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                    chain.proceed(requestBuilder.build())
                })
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()
        }
    }
}
