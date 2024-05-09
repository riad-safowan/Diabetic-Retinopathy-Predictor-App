package com.riadsafowan.diabeticretinopathypredictor

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.riadsafowan.diabeticretinopathypredictor.data.ApiService
import com.riadsafowan.diabeticretinopathypredictor.data.Repository
import com.riadsafowan.diabeticretinopathypredictor.presentation.HomeScreen
import com.riadsafowan.diabeticretinopathypredictor.ui.theme.DiabeticRetinopathyPredictorTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder().baseUrl("http://192.168.31.64:5000")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val apiService = retrofit.create(ApiService::class.java)

        enableEdgeToEdge()
        setContent {
            DiabeticRetinopathyPredictorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {

//                    var permissionGranted by remember { mutableStateOf(false) }
//
//                    val requestPermissionLauncher =
//                        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//                            permissionGranted = isGranted
//                        }
//
//                    if (!permissionGranted) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(16.dp),
//                            verticalArrangement = Arrangement.Center,
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Button(onClick = { requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES) }) {
//                                Text(text = "Request Permission")
//                            }
//                        }
//                    } else {
//                        HomeScreen(
//                            repository = Repository(apiService), modifier = Modifier.padding(it)
//                        )
//                    }
                    HomeScreen(
                        repository = Repository(apiService), modifier = Modifier.padding(it)
                    )
                }
            }
        }
    }
}
