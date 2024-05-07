package com.riadsafowan.diabeticretinopathypredictor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.riadsafowan.diabeticretinopathypredictor.data.ApiService
import com.riadsafowan.diabeticretinopathypredictor.data.Repository
import com.riadsafowan.diabeticretinopathypredictor.presentation.HomeScreen
import com.riadsafowan.diabeticretinopathypredictor.ui.theme.DiabeticRetinopathyPredictorTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder().baseUrl("http://192.168.31.64:5000")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val apiService = retrofit.create(ApiService::class.java)

        enableEdgeToEdge()
        setContent {
            DiabeticRetinopathyPredictorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(repository = Repository(apiService), modifier = Modifier.padding(it))
                }
            }
        }
    }
}
