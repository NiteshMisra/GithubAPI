package com.example.githubapi.di

import android.content.Context
import com.example.githubapi.rest.APIClient
import com.example.githubapi.rest.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(@ApplicationContext context: Context) : RetrofitClient = RetrofitClient(context)

    @Singleton
    @Provides
    fun provideApiClient(retrofitClient: RetrofitClient) =
        retrofitClient.create(APIClient::class.java)


}
