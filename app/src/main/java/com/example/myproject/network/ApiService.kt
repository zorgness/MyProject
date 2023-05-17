package com.example.myproject.network

import com.example.myproject.dataclass.*
import com.example.myproject.dataclass.activity_event.ActivityEventDto
import com.example.myproject.dataclass.activity_event.ActivityEventPostDto
import com.example.myproject.dataclass.activity_event.GetActivityByCategoryDto
import com.example.myproject.dataclass.activity_event.GetActivityEventDto
import com.example.myproject.dataclass.authentication.*
import com.example.myproject.dataclass.booking.BookingDto
import com.example.myproject.dataclass.booking.InfoBookingDto
import com.example.myproject.dataclass.category.GetCategoriesDto
import com.example.myproject.dataclass.profile.GetProfileDto
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
    suspend fun update(@Path("userId") userId: Int, @Body updateInfo: UpdateDto): Response<UserDto>?

    @GET(ApiRoutes.CATEGORY)
    suspend fun fetchAllCategories(): Response<GetCategoriesDto>?

    @GET(ApiRoutes.ACTIVITY_EVENT)
    suspend fun fetchActivityByCategory(@Query("category") categoryId: Int): Response<GetActivityByCategoryDto>?

    @GET(ApiRoutes.ACTIVITY_EVENT_BY_ID)
    suspend fun getActivityEventById(@Path("activityEventId") activityEventId: Int): Response<GetActivityEventDto>?

    @GET(ApiRoutes.USER_PROFILE)
    suspend fun fetchUserProfile(@Path("userId") userId: Int): Response<GetProfileDto>?

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.ACTIVITY_EVENT)
    suspend fun createActivityEvent(@Body activityEventInfo: ActivityEventPostDto): Response<ActivityEventDto>?

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.BOOKING)
    suspend fun addBooking(@Body bookingToPostDto: InfoBookingDto): Response<BookingDto>?

}