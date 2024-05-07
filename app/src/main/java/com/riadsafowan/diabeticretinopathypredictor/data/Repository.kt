package com.riadsafowan.diabeticretinopathypredictor.data

class Repository(private val apiService: ApiService) {
    suspend fun predictDRStage(): Result {
        return apiService.predictDRStage()
    }

    suspend fun ping(): Result {
        return apiService.ping()
    }

}