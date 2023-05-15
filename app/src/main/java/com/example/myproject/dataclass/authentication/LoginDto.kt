package com.example.myproject.dataclass.authentication

import com.example.myproject.dataclass.SerializedName

data class LoginDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
    )
