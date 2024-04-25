package com.example.curlybananasmessenger

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.curlybananasmessenger.databinding.ActivityChatInterfaceBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID

class ChatInterfaceActivity : BaseActivity() {

    private lateinit var binding: ActivityChatInterfaceBinding
    private lateinit var chatDao: ChatDao
    private lateinit var customAdapter: CustomChatMessageAdapter
    private lateinit var chatList: RecyclerView
    private lateinit var currentUser: String
    private lateinit var textViewContact: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatInterfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UI components
        textViewContact = binding.tvContactName
        chatList = binding.rvChat

        // Get data passed from previous activity
        val receiverId = intent.getStringExtra("contactId") ?: ""
        val contactName = intent.getStringExtra("contactName") ?: ""

        // Set contact name on UI
        textViewContact.text = contactName

        chatDao = ChatDao(this, receiverId)
        currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()

        binding.btnSendMessage.setOnClickListener {
            addMessage()
        }
    }

    // Method to add a message to the chat
    private fun addMessage() {
        try {
            // Get message text from input field
            val message = binding.etChatMessage.text.toString()
            val senderId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val receiverId = intent.getStringExtra("contactId").toString()
            val timeStamp = Timestamp.now()

            // Create a ChatMessage object with generated UUID
            val chatMessage =
                ChatMessage(UUID.randomUUID().toString(), message, senderId, receiverId, timeStamp)
            chatDao.addMessage(chatMessage)

            binding.etChatMessage.text.clear()

        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
    }

    // Method to display messages in the RecyclerView
    fun showMessages(messagesList: MutableList<ChatMessage>) {
        val sender = currentUser
        // Create and set up the adapter with the message list and sender ID
        customAdapter = CustomChatMessageAdapter(this, messagesList, sender)
        chatList.adapter = customAdapter
        // Set layout manager for RecyclerView
        chatList.layoutManager = LinearLayoutManager(this)
    }
}