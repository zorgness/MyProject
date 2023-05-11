package com.example.myproject.dataclass

data class LoginDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
    )
