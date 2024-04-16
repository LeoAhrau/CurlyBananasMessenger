package com.example.curlybananasmessenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel(private val repository: MessageRepository) : ViewModel() {
    val allMessages: LiveData<List<Message>> = repository.allMessages

    fun insert(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(message)
    }
}

