package com.example.myproject.dataclass

import com.squareup.moshi.Json

data class GetActivityEventDto(
    @Json(name = "activityEvent")
    val activityEvent: ActivityEventDto?
)
