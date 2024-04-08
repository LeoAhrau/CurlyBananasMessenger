package com.example.curlybananasmessenger

import androidx.room.Entity
import androidx.room.PrimaryKey
//definiera en entitet för användare i din Room-databas.

@Entity(tableName = "users")
data class User(
    @PrimaryKey val userId: String,
    val name: String,
    val email: String
)
