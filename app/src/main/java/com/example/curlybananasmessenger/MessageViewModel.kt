package com.example.curlybananasmessenger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MessageRepository
    val allMessages: LiveData<List<Message>>
    private val allMessageIds: LiveData<List<String>>

    val KEY_ID = "id"
    val KEY_TEXT = "message"

    init {
        val messageDao = AppDatabase.getDatabase(application).messageDao()
        repository = MessageRepository(messageDao)
        allMessages = repository.allMessages
        allMessageIds = repository.allMessageIds
    }

    fun insert(message: Message) = viewModelScope.launch {
        repository.insert(message)
    }

    fun deleteMessage(message: Message) = viewModelScope.launch {
        repository.deleteMessage(message)
    }

    fun addToFireStore(message: Message) = viewModelScope.launch {
        val dataToStore = HashMap<String, Any>()
        dataToStore[KEY_ID] = message.id as Any
        dataToStore[KEY_TEXT] = message.message as Any

        FirebaseFirestore
            .getInstance()
            .document("messagesTest/${message.id}")
            .set(dataToStore)

        insert(message)
    }

    fun getEveryMessage(): LiveData<List<Message>> {
        return repository.allMessages
    }

    fun getAllMessageIds(): LiveData<List<String>> {
        return repository.allMessageIds
    }
}

