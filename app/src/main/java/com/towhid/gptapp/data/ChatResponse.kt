package com.towhid.gptapp.data

data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)
