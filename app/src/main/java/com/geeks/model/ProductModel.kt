package com.geeks.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ProductCreateRequest(
    val name: String,
    val type1: String,
    val price: Int,
    val startTime: String,
    val endTime: String,
    val maxParticipant: Int,
    val destination: String,
    val thumbnailUrl: String
)

data class ProductCreateResponse(
    val id: Int,
    val name: String,
    val type1: String,
    val price: Int,
    val startTime: String,
    val endTime: String,
    val maxParticipant: Int,
    val destination: String,
    val thumbnailUrl: String,
    val status: String
)

@Parcelize
data class GetProductResponse(
    val totalCount: Int,
    val elements: List<ProductModel>
): Parcelable

@Parcelize
data class ProductModel(
    val id:Int,
    val name: String,
    val type1: String,
    val price: Int,
    val startTime: String,
    val endTime: String,
    val maxParticipant: Int,
    val curParticipant: Int,
    val destination: String,
    val thumbnailUrl: String,
    val status: String,
    val userInfo: UserInfoModel
): Parcelable

