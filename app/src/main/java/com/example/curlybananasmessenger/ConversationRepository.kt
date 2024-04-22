package com.example.curlybananasmessenger

import androidx.lifecycle.LiveData

class ConversationRepository(private val conversationDao: ConversationDao) {

    val allConversations: LiveData<List<Conversation>> = conversationDao.getAllConversations()

    suspend fun addConversation(conversation: Conversation) {
        conversationDao.addConversation(conversation)

    }
}