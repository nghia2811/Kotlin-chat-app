package com.project.firstkotlin.model

import com.google.gson.annotations.SerializedName

data class Profile (
    @SerializedName("login")
    val login:String,

    @SerializedName("avatar_url")
    val avatar_url:String
)