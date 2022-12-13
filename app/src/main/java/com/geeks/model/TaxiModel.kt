package com.geeks.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class TaxiCreateRequest(
    val price: Int,
    val startTime: String,
    val endTime: String,
    val maxParticipant: Int,
    val source: String,
    val destination: String
)

data class TaxiCreateResponse(
    val price: Int,
    val startTime: String,
    val endTime: String,
    val maxParticipant: Int,
    val source: String,
    val destination: String
)

@Parcelize
data class GetTaxiResponse(
    val totalCount: Int,
    val elements: List<ProductModel>
): Parcelable

@Parcelize
data class TaxiModel(
    val createdBy: Int,
    val createdAt: String,
    val updatedBy: Int,
    val updatedAt: String,
    val deleted: Boolean,
    val deletedAt: String,
    val id: Int,
    val userId: Int,
    val price: Int,
    val startTime: String,
    val endTime: String,
    val maxParticipant: Int,
    val source: String,
    val destination: String,
    val status: String
): Parcelable