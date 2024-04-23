package com.example.curlybananasmessenger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.curlybananasmessenger.databinding.FragmentChatInterfaceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatInterfaceFragment : Fragment() {
    private lateinit var binding: FragmentChatInterfaceBinding
    private lateinit var messageField: EditText
    private lateinit var sendButton: Button
    private lateinit var chatList: RecyclerView
    private lateinit var adapter: CustomChatMessageAdapter
    private lateinit var contactName: TextView

    private lateinit var currentUserUid: String
    private lateinit var currentContactId: String

    val chatItemList = mutableListOf<ChatMessage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatInterfaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the current user's UID
        currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Retrieve the current contact ID from arguments
        currentContactId = arguments?.getString("contactId") ?: ""

        // Initialize views
        contactName = binding.tvContactName
        messageField = binding.etChatMessage
        sendButton = binding.btnSendMessage
        chatList = binding.rvChat

        // Set contact name text view
        contactName.text = arguments?.getString("contactName")

        // Initialize adapter and layout manager for chat list
        adapter = CustomChatMessageAdapter(requireContext(), chatItemList)
        chatList.adapter = adapter
        chatList.layoutManager = LinearLayoutManager(requireContext())

        // Set onClickListener for send button
        sendButton.setOnClickListener {
            val messageText = messageField.text.toString()
            if (messageText.isNotEmpty()) {
                // Save message using the currentContactId
                saveMessage(messageText, currentContactId)
                messageField.text.clear()
            }
        }
    }

    private fun saveMessage(message: String, currentContactId: String) {
        if (currentContactId.isNotEmpty()) {
            // Construct the path to the messages collection
            val messagesCollectionPath = "users/$currentUserUid/contacts/$currentContactId/messages"

            println("currentUserUid = ${currentUserUid}")
            println("currentContactId = ${currentContactId}")

            // Create data to store in Firestore
            val messageData = hashMapOf(
                "message" to message,
                "senderId" to currentUserUid
            )

            // Add message document to Firestore under the messages collection path
            FirebaseFirestore.getInstance().collection(messagesCollectionPath)
                .add(messageData)
                .addOnSuccessListener {
                    Log.i("SUCCESS", "Message added to Firestore")
                }
                .addOnFailureListener { e ->
                    Log.e("FAILURE", "Failed to add message to Firestore", e)
                }
        } else {
            Log.e("ERROR", "Current contact ID is empty or null")
        }
    }
}