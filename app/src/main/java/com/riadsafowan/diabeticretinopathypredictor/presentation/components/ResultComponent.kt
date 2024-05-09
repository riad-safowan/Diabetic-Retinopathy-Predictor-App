package com.riadsafowan.diabeticretinopathypredictor.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.riadsafowan.diabeticretinopathypredictor.presentation.MainUiState

@Composable
fun ResultWindow(state: MainUiState) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "RESULT",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Text(text = "STAGE: ${state.stage}", fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)
        Text(
            text = "SEVERITY: ${state.severity}",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp
        )
    }
}