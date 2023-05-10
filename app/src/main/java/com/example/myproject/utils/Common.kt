package com.example.myproject.utils

import android.widget.ImageView
import com.example.myproject.R
import com.squareup.picasso.Picasso

fun myPicassoFun(url: String, ivToInsert: ImageView) {
    if (url.isEmpty()) {
        Picasso.get()
            .load(R.drawable.avatar)
            .resize(300, 300)
            .into(ivToInsert)
    } else {
        Picasso.get()
            .load(url).error(R.drawable.avatar)
            .resize(300, 300)
            .into(ivToInsert)
    }
}