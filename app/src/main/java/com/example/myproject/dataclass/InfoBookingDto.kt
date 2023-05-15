package com.example.myproject.dataclass


import com.squareup.moshi.Json

data class InfoBookingDto(
    @Json(name = "userAccount")
    val userAccount: String,
    @Json(name = "activity")
    val activity: String
)