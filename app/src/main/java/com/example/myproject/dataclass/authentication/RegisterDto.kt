package com.example.myproject.dataclass.authentication

import com.example.myproject.dataclass.SerializedName

data class RegisterDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("username") val username: String,
    @SerializedName("city") val city: String,
    @SerializedName("description") val description: String,
    @SerializedName("imageUrl") val imageUrl: String
)
