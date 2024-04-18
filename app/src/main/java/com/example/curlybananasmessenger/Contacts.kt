package com.example.curlybananasmessenger

import androidx.room.Entity
import androidx.room.PrimaryKey
//Create a Kotlin data class that describes your Entity (the contact). This class will represent
// a table in your SQLite database.

@Entity(tableName = "contacts")
data class Contacts(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String?
)


//@Entity
//data class Contacts(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val name: String,
//    val email: String?
//
//    )

