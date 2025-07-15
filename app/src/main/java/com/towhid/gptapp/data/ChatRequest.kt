package com.towhid.gptapp.data

data class ChatRequest(
    val model: String = "mistral-saba-24b",
    val messages: List<Message>,
    val temperature: Double = 0.7
)

data class Message(
    val role: String,
    val content: String
)