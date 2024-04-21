package com.example.curlybananasmessenger

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ConversationDao {
    @Query("SELECT * FROM conversation_table")
    fun getAllConversations(): LiveData<List<Conversation>>

    @Insert
    suspend fun addConversation(conversation: Conversation)
}