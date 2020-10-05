package com.project.firstkotlin

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader {
    fun load(url: String, iv: ImageView, ct: Context) =
        Glide.with(ct).load(url).into(iv)
}