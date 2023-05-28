package com.example.myproject.dto.category

import com.squareup.moshi.Json

data class GetCategoriesDto(
    @Json(name = "hydra:member")
    val categories: List<CategoryDto> = listOf()
)