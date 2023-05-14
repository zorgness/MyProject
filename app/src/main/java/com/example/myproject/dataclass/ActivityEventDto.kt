package com.example.myproject.dataclass


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
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
    @Json(name = "meetingPoint")
    val meetingPoint: String,
    @Json(name = "maxOfPeople")
    val maxOfPeople: Int,
    @Json(name = "startAt")
    val startAt: String,
    @Json(name = "category")
    val category: CategoryDto,
    @Json(name = "creator")
    val creator: CreatorDto,
    @Json(name = "meetingTime")
    val meetingTime: String,
): Parcelable