package com.geeks.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RegisterResponse(
    val email: String,
    val file: String,
    val authorityDtoSet: List<Authority>
): Parcelable

@Parcelize
data class Authority(
    val authorityName: String
): Parcelable

data class LoginRequest(
    val email: String,
    val password: String
    )

data class LoginResponse(
    val jwt: TokenModel,
    val id: Int
    )

data class TokenModel(
    val token: String
)