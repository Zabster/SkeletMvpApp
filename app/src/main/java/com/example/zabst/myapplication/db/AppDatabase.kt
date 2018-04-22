package com.example.zabst.myapplication.db

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSSION, generatedClassSeparator = "_")
object AppDatabase {
    const val NAME: String = "Mobi"
    const val VERSSION: Int = 1
}