package com.towhid.gptapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.towhid.gptapp.data.model.ChatMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel
) {

    var prompt by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(state.messages.size) {
        listState.animateScrollToItem(state.messages.size)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                { Text("Chat") },
                Modifier.background(MaterialTheme.colorScheme.onBackground)
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                items(state.messages) { msg ->
                    ChatBubble(msg)
                }
            }

            var text by remember { mutableStateOf("") }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                BasicTextField(
                    textStyle = MaterialTheme.typography.titleSmall.copy(MaterialTheme.colorScheme.onBackground),
                    value = text,
                    onValueChange = { text = it },
                    maxLines = 1,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false
                    ),
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text(
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                                text = "Type Here....",
                                fontWeight = FontWeight.Light,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = MaterialTheme.colorScheme.outlineVariant
                                ),
                                textAlign = TextAlign.Start
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    width = 1.dp,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(16.dp)
                        ) {
                            innerTextField()
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                Icons.Default.Send, contentDescription = "",
                                modifier = Modifier.clickable {
                                    viewModel.onAction(ChatAction.SendMessage(text))
                                    text = ""
                                }
                            )
                        }
                    }
                )

            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val isUser = message.role == "user"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .then(if (!isUser) Modifier.fillMaxWidth(0.8f) else Modifier)
                .padding(4.dp)
                .background(
                    color = if (isUser) Color.Blue else Color.Gray,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = if (isUser) 0.dp else 16.dp,
                        bottomStart = if (!isUser) 0.dp else 16.dp,
                        bottomEnd =  16.dp
                    )
                )
                .padding(12.dp)
        ) {
            Text(
                message.content, color = if (isUser) Color.White else Color.Black
            )
        }
    }
}
