package com.example.curlybananasmessenger

import android.content.Context
import android.os.Message
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap

class ChatDao {

    val KEY_MESSAGE_ID = "message_id"
    val KEY_MESSAGE_CONTENT = "message_content"
    val KEY_SENDER_ID = "message_sender_id"
    val KEY_RECEIVER_ID = "message_receiver_id"


    constructor(activity: ChatInterfaceActivity, receiverId: String) {

        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

        FirebaseFirestore
            .getInstance()
            .collection("messages")
            .addSnapshotListener(activity) { value, error ->
                if (error != null) {
                    Log.e("ERROR", "Failed to listen")

                }

                val messagesList = ArrayList<ChatMessage>()

                value?.forEach { document ->
                    val message_id = document.getString(KEY_MESSAGE_ID)
                    val message_content = document.getString(KEY_MESSAGE_CONTENT)
                    val message_sender_id = document.getString(KEY_SENDER_ID)
                    val message_receiver_id = document.getString(KEY_RECEIVER_ID)

                    // Check if the message is between the current user and the specified receiver
                    if ((message_sender_id == currentUserUid && message_receiver_id == receiverId) ||
                        (message_sender_id == receiverId && message_receiver_id == currentUserUid)) {
                        val chatMessage = ChatMessage(
                            message_id,
                            message_content,
                            message_sender_id,
                            message_receiver_id
                        )
                        messagesList.add(chatMessage)
                    }
                }

                Log.i("SUCCESS", "Messages retrieved from Firestore")
                activity.showMessages(messagesList)
            }
    }

    fun addMessage(chatMessage: ChatMessage) {

        val dataToStore = HashMap<String, Any>()

        dataToStore[KEY_MESSAGE_ID]=chatMessage.messageId as Any
        dataToStore[KEY_MESSAGE_CONTENT]=chatMessage.message as Any
        dataToStore[KEY_SENDER_ID]=chatMessage.senderId as Any
        dataToStore[KEY_RECEIVER_ID]=chatMessage.receiverId as Any

        FirebaseFirestore
            .getInstance()
            .document("messages/${chatMessage.messageId}")
            .set(dataToStore)
            .addOnSuccessListener { Log.i("SUCCESS", "Message saved to firebase") }
            .addOnFailureListener { Log.i("FAILURE", "Message did not save") }
    }

}