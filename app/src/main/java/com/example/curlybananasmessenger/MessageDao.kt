package com.example.curlybananasmessenger

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {
    @Query("SELECT * FROM message_table")
    fun getAllMessages(): LiveData<List<Message>>

    @Insert
    fun insertMessage(message: Message)

    @Query("SELECT * FROM message_table ORDER BY id DESC LIMIT 1")
    fun getLastMessage(): LiveData<Message>
}


