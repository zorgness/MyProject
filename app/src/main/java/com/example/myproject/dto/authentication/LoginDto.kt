package com.example.myproject.dto.authentication

import com.example.myproject.dto.SerializedName
import com.squareup.moshi.Json

/*data class LoginDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
    )*/

data class LoginDto(
    @Json(name= "email") val email: String,
    @Json(name= "password") val password: String
)

