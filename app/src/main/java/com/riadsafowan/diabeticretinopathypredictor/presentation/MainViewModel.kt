package com.riadsafowan.diabeticretinopathypredictor.presentation

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.riadsafowan.diabeticretinopathypredictor.data.ApiResource
import com.riadsafowan.diabeticretinopathypredictor.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _pickedImgUri = mutableStateOf<Uri?>(null)
    val pickedImage: State<Uri?> = _pickedImgUri

    private val _toastMessage = mutableStateOf<String?>(null)
    val toastMessage: State<String?> = _toastMessage

    private val _apiState = mutableStateOf<ApiState>(ApiState.NONE)
    val apiState: State<ApiState> = _apiState

    fun showToast(message: String?) {
        _toastMessage.value = message
    }

    init {
        _uiState.value = MainUiState(stage = 0, severity = "No DR")
    }

    fun closeImage() {
        _pickedImgUri.value = null
        _apiState.value = ApiState.NONE
    }

    fun setImage(uri: Uri?) {
        _pickedImgUri.value = uri
        _apiState.value = ApiState.NONE
    }

    fun ping() {

        viewModelScope.launch {
            when (val resource = repository.ping()) {
                is ApiResource.Success -> {
                    _uiState.value = MainUiState(
                        stage = resource.value.stage, severity = resource.value.severity
                    )
                    showToast(message = "stage: " + resource.value.stage.toString())
                }

                is ApiResource.Failure -> {
                    showToast(message = resource.errorMsg)
                }
            }
        }

    }

    fun predictDRStage(image: MultipartBody.Part) {
        _apiState.value = ApiState.LOADING
        viewModelScope.launch {

            when (val resource = repository.predictDRStage(image)) {
                is ApiResource.Success -> {
                    _apiState.value = ApiState.LOADED
                    _uiState.value = MainUiState(
                        stage = resource.value.stage, severity = resource.value.severity
                    )
//                    showToast(message = "stage: " + resource.value.stage.toString())
                }

                is ApiResource.Failure -> {
                    _apiState.value = ApiState.ERROR
                    println("riaderror: " + resource.errorMsg)
                    showToast(message = resource.errorMsg)
                }
            }
        }

    }
}

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

enum class ApiState { NONE, LOADING, LOADED, ERROR }