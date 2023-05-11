package com.example.myproject.utils

import android.widget.ImageView
import com.example.myproject.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

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

fun dateFormatter(dateStr: String): String {

    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    return parser.parse(dateStr)?.run { formatter.format(this) } ?: "01/01/1980"

}