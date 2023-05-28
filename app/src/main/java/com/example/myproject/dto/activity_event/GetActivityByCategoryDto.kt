package com.example.myproject.dto.activity_event


import com.squareup.moshi.Json

data class GetActivityByCategoryDto(
    @Json(name = "hydra:member")
    val activitiesEvent: List<ActivityEventDto> = listOf()
)