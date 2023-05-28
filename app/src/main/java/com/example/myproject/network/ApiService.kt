package com.example.myproject.network

import com.example.myproject.dto.*
import com.example.myproject.dto.activity_event.*
import com.example.myproject.dto.authentication.*
import com.example.myproject.dto.booking.BookingDto
import com.example.myproject.dto.booking.InfoBookingDto
import com.example.myproject.dto.category.GetCategoriesDto
import com.example.myproject.dto.profile.GetProfileDto
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.REGISTER)
    suspend fun register(@Body registerInfo: RegisterDto): Response<UserDto>?

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.LOGIN)
    suspend fun login(@Body loginInfo: LoginDto): Response<SessionDto>?

    @Headers("Content-Type: application/merge-patch+json")
    @PATCH(ApiRoutes.UPDATE)
    suspend fun updateUser(@Path("userId") userId: Int, @Body updateInfo: UpdateDto): Response<UserDto>?

    @Headers("Content-Type: application/json")
    @GET(ApiRoutes.CATEGORY)
    suspend fun fetchAllCategories(@HeaderMap headers: Map<String, String>): Response<GetCategoriesDto>?

    @Headers("Content-Type: application/json")
    @GET(ApiRoutes.ACTIVITY_EVENT)
    suspend fun fetchActivitiesAll(@HeaderMap headers: Map<String, String>): Response<GetActivitiesDto>?

    @Headers("Content-Type: application/json")
    @GET(ApiRoutes.ACTIVITY_EVENT)
    suspend fun fetchActivitiesByCategory(
        @HeaderMap headers: Map<String, String>,
        @Query("category") categoryId: Int,
    ): Response<GetActivitiesDto>?


    @Headers("Content-Type: application/json")
    @GET(ApiRoutes.PROFILE)
    suspend fun fetchUserProfile(
        @HeaderMap headers: Map<String, String>,
        @Path("userId") userId: Int
    ): Response<GetProfileDto>?

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.ACTIVITY_EVENT)
    suspend fun createActivityEvent(
        @HeaderMap headers: Map<String, String>,
        @Body activityEventInfo: ActivityEventPostDto
    ): Response<ActivityEventDto>?

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.BOOKING)
    suspend fun createBooking(
        @HeaderMap headers: Map<String, String>,
        @Body bookingToPostDto: InfoBookingDto
    ): Response<BookingDto>?

    @Headers("Content-Type: application/merge-patch+json")
    @PATCH(ApiRoutes.ACTIVITY_EVENT_DELETE)
    suspend fun updateActivityEvent(
        @HeaderMap headers: Map<String, String>,
        @Path("activityId") activityId: Int,
        @Body updateActivity: ActivityEventPostDto
    ): Response<ActivityEventDto>?

    @Headers("Content-Type: application/json")
    @DELETE(ApiRoutes.ACTIVITY_EVENT_DELETE)
    suspend fun deleteActivityEvent(
        @HeaderMap headers: Map<String, String>,
        @Path("activityId") activityId: Int
    ): Response<Any>?

    @DELETE(ApiRoutes.BOOKING + "/{bookingId}")
    suspend fun cancelBooking(
        @HeaderMap headers: Map<String, String>,
        @Path("bookingId") bookingId: Int
    ): Response<Any>?

}