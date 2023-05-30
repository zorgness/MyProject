package com.example.myproject.dto.activities

import com.example.myproject.dto.SerializedName


data class ActivityToPostDto(
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
