package com.geeks.model

data class JoinModel(
    val id: Int
)

data class SettleModel(
    val id: Int,
    val bankName: String,
    val accountNumber: String,
    val totalAmount: Int,
    val amount: Int
)

data class ReceiveModel(
    val id: Int,
    val pickupLocation: String,
    val pickupDatetime: String
)