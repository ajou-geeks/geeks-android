package com.geeks.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class DeliveryCreateRequest(
    val name: String,
    val type1: String,
    val minAmount: Int,
    val amount: Int,
    val startTime: String,
    val endTime: String,
    val maxParticipant: Int,
    val destination: String,
    val thumbnailUrl: String
)

data class DeliveryCreateResponse(
    val id: Int,
    val name: String,
    val type1: String,
    val minAmount: Int,
    val curAmount: Int,
    val startTime: String,
    val endTime: String,
    val maxParticipant: Int,
    val destination: String,
    val thumbnailUrl: String,
    val status: String
)

@Parcelize
data class GetDeliveryResponse(
    val totalCount: Int,
    val elements: List<DeliveryModel>
): Parcelable

@Parcelize
data class DeliveryModel(
    val id:Int,
    val name: String,
    val type1: String,
    val minAmount: Int,
    val curAmount: Int,
    val startTime: String,
    val endTime: String,
    val curParticipant: Int,
    val destination: String,
    val thumbnailUrl: String,
    val status: String,
    val userInfo: UserInfoModel
): Parcelable