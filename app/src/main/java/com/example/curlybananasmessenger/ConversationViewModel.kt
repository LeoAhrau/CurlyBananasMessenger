package com.example.curlybananasmessenger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ConversationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ConversationRepository
    val allConversations: LiveData<List<Conversation>>


    init {
        val conversationDao = AppDatabase.getDatabase(application).conversationDao()
        repository = ConversationRepository(conversationDao)
        allConversations = repository.allConversations
    }
    fun addConversation(conversation: Conversation) = viewModelScope.launch {
        repository.addConversation(conversation)
    }

    fun getEveryConversation(): LiveData<List<Conversation>> {
        return repository.allConversations
    }

}