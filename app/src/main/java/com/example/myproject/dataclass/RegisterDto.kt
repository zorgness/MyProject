package com.example.myproject.dataclass


import com.squareup.moshi.Json

data class RegisterDto(
    @Json(name = "@context")
    val context: String,
    @Json(name = "@id")
    val id: String,
    @Json(name = "@type")
    val type: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "description")
    val description: String
)