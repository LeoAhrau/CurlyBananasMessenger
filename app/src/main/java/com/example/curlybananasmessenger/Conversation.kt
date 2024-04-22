package com.example.curlybananasmessenger

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "conversation_table")
data class Conversation(
    @PrimaryKey() val id: String = "",
    val sender: String?,
    val receiver: String?)