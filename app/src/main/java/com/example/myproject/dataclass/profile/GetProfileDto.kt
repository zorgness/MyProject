package com.example.myproject.dataclass.profile


import com.example.myproject.dataclass.profile.ProfileDto
import com.squareup.moshi.Json

data class GetProfileDto(
    @Json(name = "status")
    val status: String,
    @Json(name = "profile")
    val profile: ProfileDto
)