package com.example.myproject.dto.booking


import android.os.Parcelable
import com.example.myproject.dto.activity_event.ActivityEventDto
import com.example.myproject.dto.authentication.UserDto
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "userAccount")
    val userAccount: UserDto,
    /*@Json(name = "activity")
    val activity: ActivityEventDto,*/
    @Json(name = "isPending")
    val isPending: Boolean,
    @Json(name = "isAccepted")
    val isAccepted: Boolean,
    @Json(name = "isRejected")
    val isRejected: Boolean
): Parcelable