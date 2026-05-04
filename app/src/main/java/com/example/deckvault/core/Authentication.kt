package com.example.deckvault.core

interface Authentication {

    suspend fun requestLogin(
        email: String,
        password: String
    ): ResponseService<Boolean>

    suspend fun requestSignUp(
        email: String,
        password: String
    ): ResponseService<Boolean>
}