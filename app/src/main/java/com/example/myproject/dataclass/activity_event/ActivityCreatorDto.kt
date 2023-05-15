package com.example.myproject.dataclass.activity_event


import com.squareup.moshi.Json

data class ActivityCreatorDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "location")
    val location: String,
    @Json(name = "maxOfPeople")
    val maxOfPeople: Int,
    @Json(name = "startAt")
    val startAt: String
)