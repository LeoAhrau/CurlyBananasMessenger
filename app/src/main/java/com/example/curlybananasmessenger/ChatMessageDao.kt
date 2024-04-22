package com.example.curlybananasmessenger

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class ChatMessageDao {

    private val firestoreDB = FirebaseFirestore.getInstance()
    private val usersCollection = firestoreDB.collection("chats")

    fun createMessage(chatMessage: ChatMessage) {
        val chatDetails = HashMap<String, Any>()
        chatDetails["id"] = chatMessage.id ?: ""
        chatDetails["chatMessage"] = chatMessage.chatBody ?: ""
        chatDetails["fromID"] = chatMessage.fromID ?: ""
        chatDetails["toID"] =chatMessage.toID ?: ""

        usersCollection.document(chatMessage.id ?: "").set(chatDetails)
            .addOnSuccessListener { Log.i("SUCCESS", "Successfully created message") }
            .addOnFailureListener { e ->
                Log.e("FAILURE", "Failed to create Message: ${e.message}")
            }
    }

}

