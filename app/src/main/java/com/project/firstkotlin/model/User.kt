package com.project.firstkotlin.model

data class User(var id: String = "", var email: String = "", var name: String = "", var address: String = "", var group: ArrayList<String> = arrayListOf())