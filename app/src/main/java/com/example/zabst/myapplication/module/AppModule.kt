package com.example.zabst.myapplication.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

}