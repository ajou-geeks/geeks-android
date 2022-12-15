package com.geeks.retrofit

import com.geeks.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
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

    @POST("/taxi")
    fun createTaxi(
        @Body initializeRequest: TaxiCreateRequest
    ): Call<TaxiCreateResponse>

    @POST("/product") //create product
    fun createProduct(
        @Body initializeRequest: ProductCreateRequest
    ): Call<ProductCreateResponse>

    @POST("/delivery")
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

    @GET("/delivery/list")
    fun getDeliveryList(
        /*@Query("query") query:String?,
        @Query("sort") sort: String?,
        @Query("page") page: Int?,
        @Query("count") count: Int?*/
    ): Call<GetDeliveryResponse>

    @GET("/taxi")
    fun getTaxiList(
    ): Call<List<TaxiModel>>

    @GET("/product/{id}")
    fun getProductDetail(
        @Path("id") id: Int
    ): Call<ProductModel>

    @GET("/delivery/{id}")
    fun getDeliveryDetail(
        @Path("id") id: Int
    ): Call<DeliveryModel>

    @GET("/taxi/{id}")
    fun getTaxiDetail(
        @Path("id") id: Int
    ): Call<TaxiModel>

    @POST("/roommate/profile")
    fun createRoommateProfile(
        @Body initializeRequest: CreateRoommateRequest
    ): Call<CreateRoommateResponse>

    @GET("/roommate/list")
    fun searchRoommate(
    ): Call<SearchRoommateResponse>

    @GET("/roommate/profile/{id}")
    fun getRoommateProfile(
        @Path("id") id: Int
    ): Call<RoommateModel>

    @GET("/note/room")
    fun getNoteList(
    ): Call<GetNoteResponse>

    @POST("/note")
    fun sendNote(
        @Body initializeRequest: SendNoteModel
    ): Call<GetNoteRoomResponse>

    @GET("/note")
    fun getNoteRoom(
        @Query("id") id:String?
    ): Call<GetNoteRoomResponse>

    @GET("/notice")
    fun getNoticeList(
    ): Call<GetNoticeResponse>

    @POST("/product/join")
    fun joinProduct(
        @Body initializeRequest: JoinModel
    ): Call<ProductModel>

    @POST("/product/close")
    fun closeProduct(
        @Body initializeRequest: JoinModel
    ): Call<ProductModel>

    @POST("/product/settle")
    fun settleProduct(
        @Body initializeRequest: SettleModel
    ): Call<ProductModel>

    @POST("/product/receive")
    fun receiveProduct(
        @Body initializeRequest: ReceiveModel
    ): Call<ProductModel>

    @DELETE("/product/{id}")
    fun deleteProduct(
        @Path("id") id: Int
    ): Call<Void>
}