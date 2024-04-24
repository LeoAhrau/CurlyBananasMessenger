package com.example.curlybananasmessenger

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.curlybananasmessenger.databinding.ActivityChatInterfaceBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID


class ChatInterfaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatInterfaceBinding
    private lateinit var contactName: TextView
    private lateinit var chatDao: ChatDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatInterfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val receiverId = intent.getStringExtra("contactId") ?: ""
        // Initialize chatDao
        println("receiverId = ${receiverId}")
        chatDao = ChatDao(this, receiverId)

        binding.btnSendMessage.setOnClickListener {
            addMessage()
        }
    }

    private fun addMessage() {
        try {
            val message = binding.etChatMessage.text.toString()
            val senderId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val receiverId = intent.getStringExtra("contactId").toString()

            val chatMessage =
                ChatMessage(UUID.randomUUID().toString(), message, senderId, receiverId)
            chatDao.addMessage(chatMessage)

            binding.etChatMessage.text.clear()

        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
    }

    fun showMessages(messagesList: ArrayList<ChatMessage>) {
        val chatList = ArrayAdapter(this, R.layout.simple_list_item_1, messagesList)
        binding.lvChat.adapter = chatList
    }
}