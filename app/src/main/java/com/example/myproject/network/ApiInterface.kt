package com.example.myproject.network

import com.example.myproject.dataclass.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.REGISTER)
    fun register(@Body registerInfo: RegisterInfo): Call<RegisterDto>?

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.LOGIN)
    fun login(@Body loginInfo: LoginInfo): Call<SessionDto>?

    @GET(ApiRoutes.CATEGORY)
    fun getAllCategories(): Call<GetCategoriesDto>?
}