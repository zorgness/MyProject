package com.example.myproject.dto.authentication

import com.example.myproject.dto.SerializedName
import com.squareup.moshi.Json

/*data class UpdateDto (
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("city") val city: String,
    @SerializedName("description") val description: String,
    @SerializedName("imageUrl") val imageUrl: String
)*/

data class UpdateDto (
    @Json(name= "email") val email: String,
    @Json(name="username") val username: String,
    @Json(name="city") val city: String,
    @Json(name="description") val description: String,
    @Json(name="imageUrl") val imageUrl: String
)
