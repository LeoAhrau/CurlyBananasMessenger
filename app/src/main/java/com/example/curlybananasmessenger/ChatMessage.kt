package com.example.curlybananasmessenger

data class ChatMessage(
    var message: String?,
    var senderId: String?
) {
    constructor() : this("", "")
}

