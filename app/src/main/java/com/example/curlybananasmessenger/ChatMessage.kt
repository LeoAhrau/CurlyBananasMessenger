package com.example.curlybananasmessenger

data class ChatMessage(
    val messageId: String?,
    var message: String?,
    var senderId: String?,
    var receiverId: String?
)
