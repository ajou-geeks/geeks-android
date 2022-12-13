package com.geeks.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class TaxiCreateRequest(
    val name: String,
    val type1: String,
    val price: Int,
    val startTime: String,
    val endTime: String,
    val maxParticipant: Int,
    val destination: String,
    val thumbnailUrl: String
)

data class TaxiCreateResponse(
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
data class GetTaxiResponse(
    val totalCount: Int,
    val elements: List<ProductModel>
): Parcelable

@Parcelize
data class TaxiModel(
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