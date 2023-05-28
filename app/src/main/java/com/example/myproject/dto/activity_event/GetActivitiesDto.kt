package com.example.myproject.dto.activity_event

import com.squareup.moshi.Json

data class GetActivitiesDto(
    @Json(name = "hydra:member")
    val activitiesEvent: List<ActivityEventDto> = listOf()
)