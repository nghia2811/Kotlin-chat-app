package com.project.firstkotlin.network

import com.project.firstkotlin.model.Profile
import io.reactivex.Single
import retrofit2.Call

import retrofit2.http.GET

interface GithubApi {
    @GET("users/nghia2811")
    fun getProfile(): Call<Profile>

    @GET("users/nghia2811")
    fun rxGetProfile(): Single<Profile>
}