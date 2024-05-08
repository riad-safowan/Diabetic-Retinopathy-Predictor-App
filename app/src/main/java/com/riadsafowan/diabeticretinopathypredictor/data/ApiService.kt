package com.riadsafowan.diabeticretinopathypredictor.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/api/predict-dr-stage")
    suspend fun predictDRStage(): Response<Result>

    @GET("/api/ping")
    suspend fun ping(): Response<Result>
}