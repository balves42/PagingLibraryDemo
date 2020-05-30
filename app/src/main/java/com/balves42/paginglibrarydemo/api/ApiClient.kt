package com.balves42.paginglibrarydemo.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private var BASE_URL_PREVIEW = "https://newsapi.org/v2/"

        fun getClient(): Retrofit {

            val clientBuilder = OkHttpClient.Builder()
            setClientBuilder(clientBuilder)

            return Retrofit.Builder()
                .baseUrl(BASE_URL_PREVIEW)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build()
        }

        private fun setClientBuilder(clientBuilder: OkHttpClient.Builder) {
            clientBuilder.addNetworkInterceptor(StethoInterceptor())
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)

            val interceptor = { chain: Interceptor.Chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                val responseBody = response.body()
                val source = responseBody!!.source()
                source.request(java.lang.Long.MAX_VALUE)
                response
            }
            clientBuilder.addInterceptor(interceptor)
        }
    }
}