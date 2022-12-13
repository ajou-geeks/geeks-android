package com.geeks.retrofit

import com.geeks.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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

    @POST("/product") //create product
    fun createProduct(
        @Body initializeRequest: ProductCreateRequest
    ): Call<ProductCreateResponse>

    @POST("/delivery-food")
    fun createDelivery(
        @Body initializeRequest: DeliveryCreateRequest
    ): Call<DeliveryCreateResponse>

    @GET("/product/list")
    fun getProductList(
        /*@Query("query") query:String?,
        @Query("sort") sort: String?,
        @Query("page") page: Int?,
        @Query("count") count: Int?*/
    ): Call<GetProductResponse>

    @GET("/product/{id}")
    fun getProductDetail(
        @Path("id") id: Int
    ): Call<ProductModel>
}