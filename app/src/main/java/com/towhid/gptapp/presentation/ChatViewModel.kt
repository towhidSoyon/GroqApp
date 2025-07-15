package com.towhid.gptapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towhid.gptapp.data.model.ChatMessage
import com.towhid.gptapp.data.model.ChatRequest
import com.towhid.gptapp.data.model.Message
import com.towhid.gptapp.domain.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ChatViewModel(private val repository: ChatRepository) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state : StateFlow<ChatState> = _state

    init {
        onAction(ChatAction.LoadHistory)
    }

    fun onAction(action: ChatAction){
        when(action){
            is ChatAction.SendMessage -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    try {
                        val reply = repository.sendMessage(action.prompt)
                        val updated = repository.getHistory()
                        _state.value = ChatState(messages = updated)
                    } catch (e: Exception) {
                        _state.update { it.copy(error = e.message ?: "Unknown error") }
                    }
                }
            }

            ChatAction.LoadHistory -> {
                viewModelScope.launch {
                    val history = repository.getHistory()
                    _state.value = ChatState(messages = history)
                }
            }
        }
    }
}


sealed class ChatAction{
    data class SendMessage(val prompt: String) : ChatAction()
    object LoadHistory : ChatAction()
}

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val input: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
