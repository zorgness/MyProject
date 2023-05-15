package com.example.myproject.dataclass.profile


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreatorDto(
    @Json(name = "id")
    val id: Int,
    /*@Json(name = "@type")
    val type: String,*/
    @Json(name = "email")
    val email: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "imageUrl")
    val imageUrl: String
): Parcelable