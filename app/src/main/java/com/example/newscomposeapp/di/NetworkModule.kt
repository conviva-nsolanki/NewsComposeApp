package com.example.newscomposeapp.di

import com.example.newscomposeapp.service.NewYorkTimesService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        return builder.build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Optional: Use your custom OkHttpClient
            .build()
    }

    @Provides
    fun provideNewYorkTimesService(retrofit: Retrofit): NewYorkTimesService {
        return retrofit.create(NewYorkTimesService::class.java)
    }




}