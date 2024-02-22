package com.hatchways.blog.schema

data class AuthResponse(
    val token: String,
    val id: Long,
    val username: String
)
