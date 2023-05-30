package com.example.myproject.dto.authentication

import com.example.myproject.dto.SerializedName
import com.squareup.moshi.Json

/*data class RegisterDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("username") val username: String,
    @SerializedName("city") val city: String,
    @SerializedName("description") val description: String,
    @SerializedName("imageUrl") val imageUrl: String
)*/

data class RegisterDto(
    @Json(name= "email") val email: String,
    @Json(name= "password") val password: String,
    @Json(name= "username") val username: String,
    @Json(name= "city") val city: String,
    @Json(name= "description") val description: String,
    @Json(name= "imageUrl") val imageUrl: String
)
