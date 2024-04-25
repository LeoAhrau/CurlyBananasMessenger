package com.example.curlybananasmessenger

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.curlybananasmessenger.databinding.ActivityChatInterfaceBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID


class ChatInterfaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatInterfaceBinding
    private lateinit var contactName: TextView
    private lateinit var chatDao: ChatDao
    private lateinit var customAdapter: CustomChatMessageAdapter
    private lateinit var chatList: RecyclerView
    private lateinit var currentUserMail: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatInterfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val receiverId = intent.getStringExtra("contactId") ?: ""
        // Initialize chatDao
        println("receiverId = ${receiverId}")
        chatDao = ChatDao(this, receiverId)
        currentUserMail = FirebaseAuth.getInstance().currentUser?.uid.toString()

        chatList = binding.rvChat
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
         val sender = currentUserMail
        customAdapter = CustomChatMessageAdapter(this, messagesList, sender)
        chatList.adapter = customAdapter
        chatList.layoutManager = LinearLayoutManager(this)
    }
}