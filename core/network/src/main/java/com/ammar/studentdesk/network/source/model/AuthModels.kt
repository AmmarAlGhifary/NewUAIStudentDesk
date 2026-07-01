package com.ammar.studentdesk.network.source.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val nim: String,
    val password: String
)

@Serializable
data class LoginUser(
    val nim: String
)

@Serializable
data class LoginResponse(
    val message: String,
    val token: String,
    val user: LoginUser
)