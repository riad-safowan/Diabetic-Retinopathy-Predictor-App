package com.riadsafowan.diabeticretinopathypredictor.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.glide.rememberGlidePainter
import com.riadsafowan.diabeticretinopathypredictor.data.Repository
import com.riadsafowan.diabeticretinopathypredictor.presentation.components.ResultWindow
import okhttp3.MultipartBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@SuppressLint("Recycle")
@Composable
fun HomeScreen(
    repository: Repository,
    mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(repository)),
    modifier: Modifier = Modifier
) {
    val state by mainViewModel.uiState.collectAsState()
    val context = LocalContext.current

    val pickedImgUri = mainViewModel.pickedImage.value

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            mainViewModel.setImage(uri)
        }

    val toastMessage = mainViewModel.toastMessage.value

    if (toastMessage != null) {
        Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_SHORT).show()
        mainViewModel.showToast(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (pickedImgUri != null) {
                    Image(painter = rememberGlidePainter(pickedImgUri),
                        contentDescription = "Picked Image",
                        modifier = Modifier.clickable { launcher.launch("image/*") })
                    Icon(imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(40.dp)
                            .padding(8.dp)
                            .clickable { mainViewModel.closeImage() })
                } else {
                    Text(
                        text = "Please select your\nRetina Image",
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }


        Box(
            Modifier
                .size(300.dp)
                .padding(top = 100.dp), contentAlignment = Alignment.Center
        ) {
            if (pickedImgUri != null) {
                when (mainViewModel.apiState.value) {
                    ApiState.NONE -> {
                        Button(onClick = {
                            val inputStream = context.contentResolver.openInputStream(pickedImgUri)

                            val file = File(context.cacheDir, "image.png")
                            file.createNewFile()
                            file.outputStream().use {
                                inputStream!!.copyTo(it)
                            }
                            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

                            mainViewModel.predictDRStage(
                                MultipartBody.Part.createFormData(
                                    "image", file.name, requestFile
                                )
                            )
                        }) {
                            Text(
                                text = "Predict with AI",
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier.padding(all = 16.dp)
                            )
                        }
                    }

                    ApiState.LOADING -> {
                        CircularProgressIndicator()
                    }

                    ApiState.LOADED -> {
                        ResultWindow(state)
                    }

                    ApiState.ERROR -> {
                        Text(text = "Something went wrong.")
                    }
                }
            } else {
                Button(
                    onClick = { launcher.launch("image/*") },
                ) {
                    Text(
                        text = "Pick Image",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(all = 16.dp)
                    )
                }
            }

        }

    }
}
