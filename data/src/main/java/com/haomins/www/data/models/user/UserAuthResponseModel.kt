package com.haomins.www.data.models.user

import com.google.gson.annotations.SerializedName

data class UserAuthResponseModel(

    @SerializedName("SID")
    val sid: String = "none",

    @SerializedName("LSID")
    val lsid: String = "none",

    @SerializedName("Auth")
    val auth: String = ""
)