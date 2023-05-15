package com.example.myproject.dataclass.authentication


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDto(
  /*  @Json(name = "@context")
    val context: String,
    @Json(name = "@id")
    val idHydra: String,
    @Json(name = "@type")
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