package com.project.firstkotlin.data.model

data class User(
    var id: String = "",
    var email: String = "",
    var name: String = "",
    var avatar: String = "",
    var group: ArrayList<String> = arrayListOf()
)