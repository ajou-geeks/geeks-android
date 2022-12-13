package com.geeks.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CreateRoommateRequest(
    val bio: String,
    val characterType: List<String>,
    val pattern: String,
    val patternDetail: String
)

@Parcelize
data class CreateRoommateResponse(
    val id: Int,
    val email: String,
    val password: String?,
    val name: String,
    val nickname: String,
    val profileImage: String,
    val filename: String,
    val dormitory: String,
    val ho: Int,
    val bio: String,
    val pattern: String,
    val patternDetail: String,
    val userCharacters: List<String>,
    val authority: List<Authority>
): Parcelable

data class SearchRoommateResponse(
    val totalCount: Int,
    val elements: List<RoommateModel>
)

@Parcelize
data class RoommateModel(
    val id: Int,
    val email: String,
    val password: String?,
    val name: String,
    val nickname: String,
    val profileImage: String,
    val filename: String,
    val dormitory: String,
    val ho: Int,
    val bio: String,
    val pattern: String,
    val patternDetail: String,
    val userCharacters: List<String>,
    val authority: List<Authority>,
    val score: Int
): Parcelable