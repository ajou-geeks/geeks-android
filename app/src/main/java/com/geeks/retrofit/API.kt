package com.geeks.retrofit

import com.geeks.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface API {
    @Multipart
    @POST("/auth/register") //register
    fun register(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part file: MultipartBody.Part?
    ): Call<RegisterResponse>

    @POST("/auth/login") //login
    fun login(
        @Body initializeRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("/products") //create product
    fun createProduct(
        @Body initializeRequest: ProductCreateRequest
    ): Call<ProductCreateResponse>
}