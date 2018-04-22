package com.example.zabst.myapplication.model.dbmodel

import com.example.zabst.myapplication.db.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import java.util.*

@Table(name = "users", database = AppDatabase::class)
class UserDBModel(
        @PrimaryKey(autoincrement = true)
        //@Column(name = "id")
        var id: Int = 0,

        @Column(name = "name")
        var name: String? = "",

        @Column(name = "insert_at")
        var insertIs: Calendar = Calendar.getInstance()

): BaseRXModel()
