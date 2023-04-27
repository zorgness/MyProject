package com.example.myproject.network

import com.example.myproject.dataclass.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.REGISTER)
    fun register(@Body registerInfo: RegisterInfo): Call<UserDto>?

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.LOGIN)
    fun login(@Body loginInfo: LoginInfo): Call<SessionDto>?

    @GET(ApiRoutes.CATEGORY)
    suspend fun getAllCategories(): Response<GetCategoriesDto>?

    @GET(ApiRoutes.ACTIVITY_BY_CATEGORY)
    suspend fun getActivityByCategory(@Query("category") categoryId: Int): Response<GetActivityByCategoryDto>?

    @GET(ApiRoutes.USERPROFILE + "/{userId}")
    suspend fun getUserProfile(@Path("userId") userId: Int): Response<UserDto>?
}