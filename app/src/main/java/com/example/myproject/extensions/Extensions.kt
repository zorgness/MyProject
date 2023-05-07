package com.example.myproject.extensions

import android.content.Context
import android.widget.Toast

fun Context.myToast(message: String) {
    if(message.isNotEmpty())
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}