package com.towhid.gptapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.towhid.gptapp.data.model.ChatMessage

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    suspend fun getAll(): List<ChatMessage>

    @Insert
    suspend fun insert(message: ChatMessage)

    @Query("DELETE FROM chat_messages")
    suspend fun clear()
}
