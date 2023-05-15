package com.example.myproject.dataclass.activity_event

import com.example.myproject.dataclass.SerializedName


data class ActivityEventPostDto(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("location") val location: String,
    @SerializedName("meetingPoint") val meetingPoint: String,
    @SerializedName("maxOfPeople") val maxOfPeople: Int,
    @SerializedName("startAt") val startAt: String,
    @SerializedName("category") val category: String,
    @SerializedName("creator") val creator: String,
    @SerializedName("meetingTime") val meetingTime: String,

    )
