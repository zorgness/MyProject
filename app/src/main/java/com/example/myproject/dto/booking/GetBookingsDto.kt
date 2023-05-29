package com.example.myproject.dto.booking

import com.squareup.moshi.Json

data class GetBookingsDto(
    @Json(name = "hydra:member")
    val bookings: List<BookingFullDto> = listOf()
)
