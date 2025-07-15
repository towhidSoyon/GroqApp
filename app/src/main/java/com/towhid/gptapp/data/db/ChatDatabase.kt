package com.towhid.gptapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.towhid.gptapp.data.model.ChatMessage

@Database(entities = [ChatMessage::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}