package com.geeks.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GetNoticeResponse(
    val totalCount: Int,
    val elements: List<NoticeModel>
)

@Parcelize
data class NoticeModel(
    val id: Int,
    @SerializedName("object") val _object: String,
    val title: String,
    val content: String,
    val createdAt: String
): Parcelable