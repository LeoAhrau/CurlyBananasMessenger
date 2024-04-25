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

        textViewContact = binding.tvContactName
        chatList = binding.rvChat

        val receiverId = intent.getStringExtra("contactId") ?: ""
        val contactName = intent.getStringExtra("contactName") ?: ""

        textViewContact.text = contactName

        chatDao = ChatDao(this, receiverId)
        currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()

        binding.btnSendMessage.setOnClickListener {
            addMessage()
        }
    }

    private fun addMessage() {
        try {
            val message = binding.etChatMessage.text.toString()
            val senderId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val receiverId = intent.getStringExtra("contactId").toString()
            val timeStamp = Timestamp.now()

            val chatMessage =
                ChatMessage(UUID.randomUUID().toString(), message, senderId, receiverId, timeStamp)
            chatDao.addMessage(chatMessage)

            binding.etChatMessage.text.clear()

        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
    }

    fun showMessages(messagesList: MutableList<ChatMessage>) {
        val sender = currentUser
        customAdapter = CustomChatMessageAdapter(this, messagesList, sender)
        chatList.adapter = customAdapter
        chatList.layoutManager = LinearLayoutManager(this)
    }
}