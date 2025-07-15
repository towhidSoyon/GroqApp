package com.towhid.gptapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towhid.gptapp.data.ChatRequest
import com.towhid.gptapp.data.Message
import com.towhid.gptapp.domain.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ChatViewModel : ViewModel() {
    private val repository = ChatRepository()

    private val _state = MutableStateFlow(ChatState())
    val state : StateFlow<ChatState> = _state

    fun onAction(action: ChatAction){
        when(action){
            is ChatAction.SendPrompt -> {
                _state.value = ChatState(isLoading = true)
                viewModelScope.launch {
                    val req = ChatRequest(messages = listOf(Message("user", action.prompt)))
                    val result = repository.getResponse(req)
                    _state.value = if (result.isSuccessful) {
                        ChatState(response = result.body()?.choices?.firstOrNull()?.message?.content ?: "Empty response")
                    } else {
                        ChatState(error = "Error: ${result.message()}")
                    }
                }
            }
        }
    }
}


sealed class ChatAction{
    data class SendPrompt(val prompt: String) : ChatAction()
}

data class ChatState(
    val isLoading: Boolean = false,
    val response: String = "",
    val error: String? = null
)