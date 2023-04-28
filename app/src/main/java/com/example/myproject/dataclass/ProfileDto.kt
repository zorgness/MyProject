package com.example.myproject.dataclass


import com.squareup.moshi.Json

data class ProfileDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "username")
    val username: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "description")
    val description: String
)