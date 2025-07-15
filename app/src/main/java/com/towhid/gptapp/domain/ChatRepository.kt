package com.towhid.gptapp.domain

import com.towhid.gptapp.BuildConfig
import com.towhid.gptapp.data.ChatRequest
import com.towhid.gptapp.data.api.RetrofitClient

class ChatRepository {
    private val api = RetrofitClient.api
    private val apiKey = BuildConfig.GROQ_API_KEY

    suspend fun getResponse(request: ChatRequest) = api.getChatCompletion("Bearer $apiKey", request)
}