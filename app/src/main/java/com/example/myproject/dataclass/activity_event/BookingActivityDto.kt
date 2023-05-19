package com.example.myproject.dataclass.activity_event

import android.os.Parcelable
import com.example.myproject.dataclass.booking.BookingDto
import com.example.myproject.dataclass.category.CategoryDto
import com.example.myproject.dataclass.profile.CreatorDto
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
@Parcelize
data class BookingActivityDto(
@Json(name = "@id")
val idHydra: String?,
/*  @Json(name = "@type")
  val type: String,*/
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

