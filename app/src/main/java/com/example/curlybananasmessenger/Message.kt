package com.example.curlybananasmessenger

import androidx.room.Entity
import androidx.room.PrimaryKey
//En entitet i Room representerar en tabell i databasen.
// För en chattapp har vi en entitet som representerar ett meddelande.

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val content: String,
    val timestamp: Long
)
