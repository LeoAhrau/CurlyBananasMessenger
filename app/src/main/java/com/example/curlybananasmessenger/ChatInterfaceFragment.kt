package com.example.curlybananasmessenger

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.curlybananasmessenger.databinding.FragmentChatInterfaceBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class ChatInterfaceFragment : Fragment() {
    private lateinit var binding: FragmentChatInterfaceBinding
    private lateinit var messageField: EditText
    private lateinit var sendButton: Button
    private lateinit var chatList: RecyclerView
    private lateinit var adapter: CustomChatMessageAdapter
    private lateinit var contactName: TextView
    private lateinit var messageViewModel: MessageViewModel
    private var idList: List<String>? = listOf()
    private var textOfMessages: MutableList<Message>? = mutableListOf()
    private lateinit var receiver: String
    lateinit var sender: String

    private val KEY_ID = "id"
    private val KEY_TEXT = "message"
    val KEY_SENDER = "user_sender"
    val KEY_RECEIVER = "user_receiver"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        messageViewModel = ViewModelProvider(this)[MessageViewModel::class.java]
        messageViewModel.getAllMessageIds().observe(requireActivity()) { ids ->
            idList = ids
        }
        messageViewModel.getEveryMessage().observe(requireActivity()) { messages ->
            textOfMessages = messages.map { message ->
                message

            }.toMutableList()

            adapter = CustomChatMessageAdapter(requireContext(), textOfMessages!!, sender)
            chatList.adapter = adapter
            chatList.layoutManager = LinearLayoutManager(requireContext())

        }
        binding = FragmentChatInterfaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkFireStoreCollection()
        contactName = binding.contactName
        messageField = binding.etChatMessage
        sendButton = binding.btnSendMessage
        chatList = binding.rvChat

        try {
            val contactName = arguments?.getString("contactName")
            binding.contactName.text = contactName
            sender = arguments?.getString("contactSender").toString()
            receiver = arguments?.getString("contactReceiver").toString()

            Log.i("Success", "Successfully changed textview name")
        } catch (e: Exception) {
            Log.e("Error", "Failed to change name", e)
        }

        sendButton.setOnClickListener {
            addMessage()
        }
    }

    private fun checkFireStoreCollection() {
        FirebaseFirestore
            .getInstance()
            .collection("messagesTest")
            .addSnapshotListener(requireActivity()) { value, error ->
                if (error != null) {
                    Log.e("ERROR", "failed to listen for updates")
                }
                if (value != null) {

                    for (document in value) {
                        val id = document.getString(KEY_ID) as String
                        val text = document.getString(KEY_TEXT) as String
                        val sender = document.getString(KEY_SENDER) as String
                        val receiver = document.getString(KEY_RECEIVER) as String

                        val message = Message(id, text, sender, receiver)

                        if (idList?.contains(document.id) == false) {
                            messageViewModel.insert(message)
                        }
                    }
                    Log.i("SUCCESS", "Snapshot success")
                }
            }
    }
    private fun addMessage() {
        try {
            val text = messageField.text.toString()
            val message =
                Message(id = UUID.randomUUID().toString(), message = text, sender, receiver)
            if (text.isNotEmpty()) {
                messageViewModel.addToFireStore(message)
                messageField.text.clear()
            }
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
            Toast.makeText(requireContext(), "Error adding user to database", Toast.LENGTH_SHORT).show()
        }
    }
}