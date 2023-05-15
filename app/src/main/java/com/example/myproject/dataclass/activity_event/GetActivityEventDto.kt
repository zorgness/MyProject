package com.example.myproject.dataclass.activity_event

import com.example.myproject.dataclass.activity_event.ActivityEventDto
import com.squareup.moshi.Json

data class GetActivityEventDto(
    @Json(name = "status")
    val status: Int,
    @Json(name = "activityEvent")
    val activityEvent: ActivityEventDto?
)
