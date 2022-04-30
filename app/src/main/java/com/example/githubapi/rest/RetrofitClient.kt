package com.example.githubapi.rest

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(private val context: Context) {

    private val mRetrofit: Retrofit

    // Customize the request
    private fun getAuthHttpClient() : OkHttpClient.Builder {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(120, TimeUnit.SECONDS)
            httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                // Customize the request
                val request = chain.request().newBuilder()
                    .method(chain.request().method, chain.request().body)
                    .build()
                chain.proceed(request)
            })
            val mLogging = HttpLoggingInterceptor()
            mLogging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(mLogging)
            httpClient.readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
            return httpClient
        }

    fun <T> create(javaClass: Class<T>?): T {
        return mRetrofit.create(javaClass)
    }

    init {
        val gson = GsonBuilder().setLenient().create()
        val client: OkHttpClient = getAuthHttpClient()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
        mRetrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }
}