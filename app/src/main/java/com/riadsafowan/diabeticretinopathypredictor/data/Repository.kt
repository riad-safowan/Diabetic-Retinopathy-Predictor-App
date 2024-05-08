package com.riadsafowan.diabeticretinopathypredictor.data

class Repository(private val apiService: ApiService) : ApiCall {
    suspend fun predictDRStage(): ApiResource<Result> = apiCall { apiService.predictDRStage() }

    suspend fun ping(): ApiResource<Result> = apiCall { apiService.ping() }

}