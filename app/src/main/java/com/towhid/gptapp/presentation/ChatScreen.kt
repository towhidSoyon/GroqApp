package com.towhid.gptapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel()
) {

    val state by viewModel.state.collectAsState()
    var prompt by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text("Ask something") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.onAction(ChatAction.SendPrompt(prompt)) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Send")
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        if (state.response.isNotEmpty()) {
            Text(state.response, modifier = Modifier.padding(top = 16.dp))
        }

        state.error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }
    }

}