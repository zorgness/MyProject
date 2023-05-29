package com.example.myproject.dto.activities


import com.example.myproject.dto.profile.CreatorDto
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
    val startAt: String,
    @Json(name = "creator")
    val creator: CreatorDto,
)