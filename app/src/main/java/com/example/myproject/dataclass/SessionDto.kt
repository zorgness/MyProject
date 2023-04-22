package com.example.myproject.dataclass


import com.squareup.moshi.Json

data class SessionDto(
    @Json(name = "token")
    val token: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "username")
    val username: String
)