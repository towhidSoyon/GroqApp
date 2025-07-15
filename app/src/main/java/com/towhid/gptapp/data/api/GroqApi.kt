package com.towhid.gptapp.data.api

import com.towhid.gptapp.data.model.ChatRequest
import com.towhid.gptapp.data.model.ChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GroqApi {
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(
        @Header("Authorization") auth: String,
        @Body chatRequest: ChatRequest
    ): Response<ChatResponse>
}