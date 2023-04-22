package com.example.myproject.dataclass


import com.squareup.moshi.Json

data class CategoryDto(
    @Json(name = "@id")
    val idHydra: String,
    @Json(name = "@type")
    val type: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "url_image")
    val urlImage: String,
    @Json(name = "urlImage")
    val urlImage2: String
)