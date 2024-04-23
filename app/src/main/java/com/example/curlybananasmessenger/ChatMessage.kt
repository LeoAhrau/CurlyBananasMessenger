package com.example.curlybananasmessenger

data class ChatMessage(
    var message: String? = null,
    var senderId: String? = null
) {
    constructor() : this("", "")
}

