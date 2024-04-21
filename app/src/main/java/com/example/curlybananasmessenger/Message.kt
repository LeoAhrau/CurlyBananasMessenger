package com.example.curlybananasmessenger


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (tableName = "message_table",foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_sender"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_receiver"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Message(
    @PrimaryKey() val id: String = "",
    @ColumnInfo(name = "text")
    val message: String = "",
    @ColumnInfo(name = "user_sender")
    val userSender: String = "",
    @ColumnInfo(name = "user_receiver")
    val userReceiver: String = ""
)




//import androidx.room.Entity
//import androidx.room.PrimaryKey
////En entitet i Room representerar en tabell i databasen.
//// För en chattapp har vi en entitet som representerar ett meddelande.
//
//@Entity
//data class Message(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val userId: Int,
//    val content: String,
//    val timestamp: Long
//)
