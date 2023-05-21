package com.example.myproject.dataclass.booking


import android.os.Parcelable
import com.example.myproject.dataclass.activity_event.ActivityEventDto
import com.example.myproject.dataclass.activity_event.BookingActivityDto
import com.example.myproject.dataclass.authentication.UserDto
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "userAccount")
    val userAccount: UserDto,
  /*  @Json(name = "activity")
    val activity: BookingActivityDto,*/
    @Json(name = "isPending")
    val isPending: Boolean,
    @Json(name = "isAccepted")
    val isAccepted: Boolean,
    @Json(name = "isRejected")
    val isRejected: Boolean
): Parcelable