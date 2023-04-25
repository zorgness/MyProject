package com.example.myproject.dataclass


import com.squareup.moshi.Json

data class GetActivityByCategoryDto(
    @Json(name = "hydra:member")
    val activitiesEvent: List<ActivityEventDto> = listOf()
)