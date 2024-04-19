package com.example.curlybananasmessenger

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {
    @Query("SELECT * FROM message_table")
    fun getAllMessages(): LiveData<List<Message>>

    @Query("SELECT id FROM message_table")
    fun getAllMessageIds(): LiveData<List<String>>

    @Insert
    suspend fun insertMessage(message: Message)
    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("SELECT * FROM message_table ORDER BY id DESC LIMIT 1")
    fun getLastMessage(): LiveData<Message>
}


