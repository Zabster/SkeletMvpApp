package com.example.zabst.myapplication.net

import com.example.zabst.myapplication.model.responsemodel.ResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("/api/get")
    fun getData(@Query("name") resName: String, @Query("num") count: Int): Observable<List<ResponseModel>>
}