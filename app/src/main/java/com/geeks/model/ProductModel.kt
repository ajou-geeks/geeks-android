package com.geeks.model

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