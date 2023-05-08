package com.example.myproject.dataclass

import com.squareup.moshi.Json

data class GetActivityEventDto(
    @Json(name = "status")
    val status: Int,
    @Json(name = "activityEvent")
    val activityEvent: ActivityEventDto?
)
