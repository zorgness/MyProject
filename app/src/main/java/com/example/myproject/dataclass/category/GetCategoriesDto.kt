package com.example.myproject.dataclass.category

import com.example.myproject.dataclass.category.CategoryDto
import com.squareup.moshi.Json

data class GetCategoriesDto(
    @Json(name = "hydra:member")
    val categories: List<CategoryDto> = listOf()
)