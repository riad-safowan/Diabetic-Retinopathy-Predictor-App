package com.riadsafowan.diabeticretinopathypredictor.data

import okhttp3.MultipartBody

class Repository(private val apiService: ApiService) : ApiCall {
    suspend fun predictDRStage(image: MultipartBody.Part): ApiResource<Result> = apiCall { apiService.predictDRStage(image) }

    suspend fun ping(): ApiResource<Result> = apiCall { apiService.ping() }

}