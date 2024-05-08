package com.riadsafowan.diabeticretinopathypredictor.data

import retrofit2.Response
import java.io.IOException

interface ApiCall {
    suspend fun <T> apiCall(
        apiCall: suspend () -> Response<T>
    ): ApiResource<T> {
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    ApiResource.Success(data)
                } else {
                    ApiResource.Failure("Response body is empty")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                ApiResource.Failure(errorBody)
            }
        } catch (e: IOException) {
            ApiResource.Failure("Network error: ${e.message}")
        } catch (e: Throwable) {
            ApiResource.Failure("Unexpected error: ${e.message}")
        }
    }
}