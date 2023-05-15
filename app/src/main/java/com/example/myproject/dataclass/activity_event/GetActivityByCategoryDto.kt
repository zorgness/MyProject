package com.example.myproject.dataclass.activity_event


import com.example.myproject.dataclass.activity_event.ActivityEventDto
import com.squareup.moshi.Json

data class GetActivityByCategoryDto(
    @Json(name = "hydra:member")
    val activitiesEvent: List<ActivityEventDto> = listOf()
)