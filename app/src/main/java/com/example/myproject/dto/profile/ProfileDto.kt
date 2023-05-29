package com.example.myproject.dto.profile


import com.example.myproject.dto.booking.BookingDto
import com.example.myproject.dto.activities.ActivityEventDto
import com.squareup.moshi.Json

data class ProfileDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "username")
    val username: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "imageUrl")
    val imageUrl: String,
    @Json(name = "activities")
    val activities: List<ActivityEventDto> = listOf(),
    @Json(name = "bookings")
    val bookings: List<BookingDto>  = listOf()
)