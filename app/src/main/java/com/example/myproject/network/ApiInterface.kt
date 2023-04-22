package com.example.myproject.network

import com.example.myproject.dataclass.GetCategoriesDto
import com.example.myproject.dataclass.LoginInfo
import com.example.myproject.dataclass.RegisterDto
import com.example.myproject.dataclass.SessionDto
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST(ApiRoutes.REGISTER)
    fun register(@Field("email") email: String, @Field("password") password: String, @Field("username") username: String, @Field("city") city: String, @Field("description") description: String): Call<RegisterDto>?

    @Headers("Content-Type: application/json")
    @POST(ApiRoutes.LOGIN)
    fun login(@Body loginInfo: LoginInfo): Call<SessionDto>?


    @GET(ApiRoutes.CATEGORY)
    fun getAllCategories(): Call<GetCategoriesDto>?
}