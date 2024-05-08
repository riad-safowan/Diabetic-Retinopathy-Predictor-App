package com.riadsafowan.diabeticretinopathypredictor.presentation

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

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _toastMessage = mutableStateOf<String?>(null)
    val toastMessage: State<String?> = _toastMessage

    fun showToast(message: String?) {
        _toastMessage.value = message
    }

    init {
        _uiState.value = MainUiState(stage = 0, severity = "No DR")
    }

    fun ping() {

        viewModelScope.launch {
            when (val resource = repository.ping()) {
                is ApiResource.Success -> {
                    _uiState.value = MainUiState(
                        stage = resource.value.stage, severity = resource.value.severity
                    )
                }

                is ApiResource.Failure -> {
                    showToast(message = "Something went wrong.")
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