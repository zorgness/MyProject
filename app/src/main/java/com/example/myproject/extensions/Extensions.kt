package com.example.myproject.extensions

import android.content.Context
import android.widget.Toast

fun Context.myToast(message: String) {
    if(message.isNotEmpty())
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


/**
 * Adapt id for Symfony api platform, Hydra is, in short an extension to JSON-LD
 */
fun Int.toHydraUserId(): String {
    return "api/users/$this"
}

fun Int.toHydraCategoryId(): String {
    return "api/categories/$this"
}

fun String.isLongEnough(): Boolean {
    return this.length >= 6
}