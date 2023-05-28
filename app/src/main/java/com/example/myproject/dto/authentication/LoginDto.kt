package com.example.myproject.dto.authentication

import com.example.myproject.dto.SerializedName

data class LoginDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
    )
