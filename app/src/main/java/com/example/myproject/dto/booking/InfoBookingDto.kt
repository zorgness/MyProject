package com.example.myproject.dto.booking


import com.example.myproject.dto.SerializedName
import com.squareup.moshi.Json

data class InfoBookingDto(
    @Json(name="userAccount") val userAccount: String,
    @Json( name="activity") val activity: String
)