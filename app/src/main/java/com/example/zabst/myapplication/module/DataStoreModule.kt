package com.example.zabst.myapplication.module

import android.app.Application
import com.example.zabst.myapplication.helper.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataStoreModule {

    @Provides
    @Singleton
    fun provideSharePreference(application: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(application.applicationContext)
    }
}