package com.example.myproject.dataclass


import com.squareup.moshi.Json

data class BookingDto(
    @Json(name = "userAccount")
    val userAccount: UserDto,
    @Json(name = "activity")
    val activity: ActivityEventDto,
    @Json(name = "isPending")
    val isPending: Boolean,
    @Json(name = "isAccepted")
    val isAccepted: Boolean,
    @Json(name = "isRejected")
    val isRejected: Boolean
)