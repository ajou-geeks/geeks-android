package com.geeks.model

data class DeliveryCreateRequest(
    val name: String,
    val type1: String,
    val minPrice: Int,
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
    val minPrice: Int,
    val amount: Int,
    val startTime: String,
    val endTime: String,
    val maxParticipant: Int,
    val destination: String,
    val thumbnailUrl: String,
    val status: String
)