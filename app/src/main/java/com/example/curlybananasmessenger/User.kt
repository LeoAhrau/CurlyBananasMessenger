package com.example.curlybananasmessenger

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class User (
    @PrimaryKey() val id: String = "",
    val nickname: String?,
    val username: String?,
    val password: String?)

