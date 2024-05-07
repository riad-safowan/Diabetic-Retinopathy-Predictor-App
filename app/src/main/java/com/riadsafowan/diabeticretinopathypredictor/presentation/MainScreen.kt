package com.riadsafowan.diabeticretinopathypredictor.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.riadsafowan.diabeticretinopathypredictor.data.Repository

@Composable
fun HomeScreen(
    repository: Repository,
    mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(repository)),
    modifier: Modifier = Modifier
) {
    val state by mainViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Stage: ${state.stage}",
            fontSize = 30.sp,
            color = Color.Black,
            modifier = Modifier.padding(all = 10.dp)
        )
        Text(
            text = "Severity: ${state.severity}",
            fontSize = 30.sp,
            color = Color.Black,
            modifier = Modifier.padding(all = 10.dp)
        )
        Button(onClick = { mainViewModel.ping() }, modifier = Modifier.padding(top = 30.dp)) {
            Text(
                text = "Ping",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(all = 2.dp)

            )
        }

    }
}