package com.example.myproject.dataclass


import com.squareup.moshi.Json

data class ActivityEventDto(
    @Json(name = "@id")
    val idHydra: String,
    @Json(name = "@type")
    val type: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "location")
    val location: String,
    @Json(name = "meeting_point")
    val meetingPoint: String,
    @Json(name = "max_of_people")
    val maxOfPeople: Int,
    @Json(name = "start_at")
    val startAt: String,
    @Json(name = "category")
    val category: String,
    @Json(name = "creator")
    val creator: String,
)