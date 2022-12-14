package com.geeks.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfoModel(
    val id: Int,
    val nickname: String,
    val dormitory: String
): Parcelable

@Parcelize
data class OtherInfoModel(
    val id: Int,
    val nickname: String,
    val profileImage: String
): Parcelable