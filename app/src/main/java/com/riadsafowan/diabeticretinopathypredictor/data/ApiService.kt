package com.riadsafowan.diabeticretinopathypredictor.data

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/api/predict-dr-stage")
    suspend fun predictDRStage(@Part image: MultipartBody.Part): Response<Result>


    @GET("/api/ping")
    suspend fun ping(): Response<Result>
}