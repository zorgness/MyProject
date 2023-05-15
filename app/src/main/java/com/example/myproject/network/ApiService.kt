package com.example.myproject.network

import com.example.myproject.dataclass.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.REGISTER)
    suspend fun register(@Body registerInfo: RegisterDto): Response<UserDto>?

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.LOGIN)
    suspend fun login(@Body loginInfo: LoginDto): Response<SessionDto>?

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