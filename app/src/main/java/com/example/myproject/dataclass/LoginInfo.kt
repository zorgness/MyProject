package com.example.myproject.dataclass

data class LoginInfo(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
    )
