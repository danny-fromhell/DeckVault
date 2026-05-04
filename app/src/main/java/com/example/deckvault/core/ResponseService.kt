package com.example.deckvault.core

sealed class ResponseService<out T> {

    object Loading : ResponseService<Nothing>()

    data class Success<out T>(
        val data: T
    ) : ResponseService<T>()

    data class Error(
        val message: String
    ) : ResponseService<Nothing>()
}