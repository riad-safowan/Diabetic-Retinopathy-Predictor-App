package com.riadsafowan.diabeticretinopathypredictor.data

sealed class ApiResource<out T> {

    data class Success<out T>(val value: T) : ApiResource<T>()

    data class Failure(
        val errorMsg: String?
    ) : ApiResource<Nothing>()
}