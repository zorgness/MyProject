package com.example.myproject.dto.booking

import com.example.myproject.dto.activities.ActivityCreatorDto
import com.example.myproject.dto.activities.ActivityEventDto
import com.example.myproject.dto.authentication.UserDto
import com.squareup.moshi.Json

data class BookingFullDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "userAccount")
    val userAccount: UserDto,
    @Json(name = "activity")
    val activity: ActivityCreatorDto,
    @Json(name = "isPending")
    val isPending: Boolean,
    @Json(name = "isAccepted")
    val isAccepted: Boolean,
    @Json(name = "isRejected")
    val isRejected: Boolean
)
