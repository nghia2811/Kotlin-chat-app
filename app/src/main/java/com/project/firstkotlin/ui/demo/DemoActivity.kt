package com.project.firstkotlin.ui.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.firstkotlin.R
import com.project.firstkotlin.data.model.Profile
import com.project.firstkotlin.data.remote.GithubApi
import com.project.firstkotlin.ImageLoader
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_demo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DemoActivity : AppCompatActivity() {

    @Inject
    lateinit var githubApi: GithubApi

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        githubApi.getProfile()
            .enqueue(object : Callback<Profile> {
                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    txt_name.text = response.body()?.login
                    imageLoader.load(response.body()!!.avatar_url, image_avatar, this@DemoActivity)
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    txt_name.text = t.message
                }
            })

//        githubApi.rxGetProfile()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()

    }
}