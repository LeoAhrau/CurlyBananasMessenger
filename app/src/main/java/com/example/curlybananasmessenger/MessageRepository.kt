package com.example.curlybananasmessenger

import android.util.Log
import androidx.lifecycle.LiveData


class MessageRepository(private val messageDao: MessageDao) {
    val allMessages: LiveData<List<Message>> = messageDao.getAllMessages()

    val allMessageIds: LiveData<List<String>> = messageDao.getAllMessageIds()

    suspend fun insert(message: Message) {
        messageDao.insertMessage(message)

    }
    suspend fun deleteMessage(message: Message) {
        messageDao.deleteMessage(message)
    }
}

