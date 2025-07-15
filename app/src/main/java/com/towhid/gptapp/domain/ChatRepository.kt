package com.towhid.gptapp.domain

import com.towhid.gptapp.BuildConfig
import com.towhid.gptapp.data.api.GroqApi
import com.towhid.gptapp.data.db.ChatDao
import com.towhid.gptapp.data.model.ChatMessage
import com.towhid.gptapp.data.model.ChatRequest
import com.towhid.gptapp.data.model.Message

class ChatRepository(
    private val api: GroqApi,
    private val dao: ChatDao,
    private val apiKey: String
) {
    suspend fun sendMessage(prompt: String): ChatMessage {
        val request = ChatRequest(
            model = "mistral-saba-24b",
            messages = listOf(Message("user", prompt))
        )
        val response = api.getChatCompletion("Bearer $apiKey", request)
        val reply = response.body()?.choices?.firstOrNull()?.message?.content ?: "No reply"
        val assistantMsg = ChatMessage(role = "assistant", content = reply)
        dao.insert(ChatMessage(role = "user", content = prompt))
        dao.insert(assistantMsg)
        return assistantMsg
    }

    suspend fun getHistory(): List<ChatMessage> = dao.getAll()
}
