package com.example.myproject.network

import com.example.myproject.dataclass.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.REGISTER)
    fun register(@Body registerInfo: RegisterInfo): Call<UserDto>?

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.LOGIN)
    suspend fun login(@Body loginInfo: LoginInfo): Response<SessionDto>?

    @GET(ApiRoutes.CATEGORY)
    suspend fun getAllCategories(): Response<GetCategoriesDto>?

    @GET(ApiRoutes.ACTIVITY_BY_CATEGORY)
    suspend fun getActivityByCategory(@Query("category") categoryId: Int): Response<GetActivityByCategoryDto>?

    @GET(ApiRoutes.USERPROFILE)
    suspend fun getUserProfile(@Path("userId") userId: Int): Response<GetProfileDto>?
}