package com.example.myproject.dataclass


import com.squareup.moshi.Json

data class GetProfileDto(
    @Json(name = "status")
    val status: String,
    @Json(name = "profile")
    val profile: ProfileDto
)