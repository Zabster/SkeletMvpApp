package com.example.zabst.myapplication.helper.dbhelper

import android.util.Log
import com.example.zabst.myapplication.model.dbmodel.UserDBModel
import com.example.zabst.myapplication.model.dbmodel.UserDBModel_Table
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.rx2.kotlinextensions.insert
import com.raizlabs.android.dbflow.sql.language.Select

object UserDBHelper {

    fun getByName(id:Int): UserDBModel? {
        return Select()
                .from(UserDBModel::class.java)
                .where(UserDBModel_Table.id eq id)
                .querySingle()
    }

    fun setUser(name: String): Long {
        var complite = (-1).toLong()
        UserDBModel(name = name).insert{ rowId ->
            Log.d("Save to BD", "success - $rowId")
            complite = rowId
        }
        return complite
    }
}