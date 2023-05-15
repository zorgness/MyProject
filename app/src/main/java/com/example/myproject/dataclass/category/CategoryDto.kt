package com.example.myproject.dataclass.category


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDto(
  /*  @Json(name = "@id")
    val idHydra: String,*/
  /*  @Json(name = "@type")
    val type: String,*/
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "urlImage")
    val urlImage: String,

): Parcelable