package com.example.locationupdateapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class SharedPreferenceModule {

    @Singleton
    @Provides
    fun getSharePreference(@ApplicationContext appContext: Context) :SharedPreferences
    {
        val prefs = appContext.getSharedPreferences("GeoSpark", AppCompatActivity.MODE_PRIVATE)
        return prefs

    }
}