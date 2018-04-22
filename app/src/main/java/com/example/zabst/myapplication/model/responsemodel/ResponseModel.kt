package com.example.zabst.myapplication.model.responsemodel

import com.google.gson.annotations.SerializedName

data class ResponseModel(@SerializedName("site") var site: String,
                         @SerializedName("name") var name: String,
                         @SerializedName("desc") var desc: String,
                         @SerializedName("link") var link: String,
                         @SerializedName("elementPureHtml") var elementPureHtml: String)
