package com.example.newscomposeapp.di

import android.content.Context
import com.conviva.apptracker.ConvivaAppAnalytics
import com.conviva.apptracker.controller.TrackerController
import com.example.newscomposeapp.utils.CONVIVA_APP_NAME
import com.example.newscomposeapp.utils.CONVIVA_CUSTOMER_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providerConvivaAppTracker(@ApplicationContext context: Context): TrackerController {
        val tracker: TrackerController = ConvivaAppAnalytics.createTracker(
            context,
            CONVIVA_CUSTOMER_ID,
            CONVIVA_APP_NAME,
        )!!
        return tracker
    }
}