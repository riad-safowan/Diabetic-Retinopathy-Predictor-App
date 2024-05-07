package com.riadsafowan.diabeticretinopathypredictor.data

import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/api/predict-dr-stage")
    suspend fun predictDRStage(): Result

    @GET("/api/ping")
    suspend fun ping(): Result
}