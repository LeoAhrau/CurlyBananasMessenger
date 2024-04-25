package com.example.curlybananasmessenger

import com.google.firebase.Timestamp

data class ChatMessage(
    val messageId: String?,
    var message: String?,
    var senderId: String?,
    var receiverId: String?,
    val timestamp: Timestamp?
)
