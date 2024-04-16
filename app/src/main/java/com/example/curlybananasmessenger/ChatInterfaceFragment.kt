package com.example.curlybananasmessenger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.curlybananasmessenger.databinding.FragmentChatInterfaceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.protobuf.Value

class ChatInterfaceFragment : Fragment() {
    private lateinit var binding: FragmentChatInterfaceBinding
    private lateinit var messageField: EditText
    private lateinit var sendButton: Button

    private lateinit var chatList: RecyclerView
    private lateinit var adapter: CustomChatMessageAdapter
    private lateinit var contactName: TextView
    private lateinit var dbRef: DatabaseReference

    private val chatItemList = mutableListOf<ChatMessage>()

    private val messageCollection = FirebaseFirestore.getInstance().collection("messages")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentChatInterfaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = Intent()


        try {
            val contactName = arguments?.getString("contactId")
            binding.tvContactName.text = contactName

            Log.i("Success", "Successfully changed textview name")
        } catch (e: Exception) {
            Log.e("Error", "Failed to change name", e)
        }

        messageField = binding.etChatMessage
        sendButton = binding.btnSendMessage
        chatList = binding.rvChat

        adapter = CustomChatMessageAdapter(requireContext(), chatItemList)
        chatList.adapter = adapter
        chatList.layoutManager = LinearLayoutManager(requireContext())

        messageCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("Firestore", "Error listening for messages")
                return@addSnapshotListener
            }

            snapshot?.let {
                for (doc in it.documentChanges) {
                    val message = doc.document.toObject(ChatMessage::class.java)
                    chatItemList.add(message)
                    adapter.notifyItemInserted(chatItemList.size - 1)

                }
                sendButton.setOnClickListener {

                    val messageText = messageField.text.toString()
                    sendMessage(messageText)

                }
            }
        }
    }

    private fun sendMessage (messageText: String) {
        val message = ChatMessage(messageText, FirebaseAuth.getInstance().currentUser?.uid)
        messageCollection.add(message)
            .addOnSuccessListener { Log.i ("Firestore", "Message sent and stored succesfully") }
            .addOnFailureListener { Log.e ("Firestore", "Error sending message") }

    }

}
