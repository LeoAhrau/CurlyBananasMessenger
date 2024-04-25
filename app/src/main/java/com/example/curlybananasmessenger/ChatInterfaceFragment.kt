//package com.example.curlybananasmessenger
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.curlybananasmessenger.databinding.FragmentChatInterfaceBinding
//import com.google.android.gms.tasks.Task
//import com.google.android.gms.tasks.Tasks
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.QuerySnapshot
//
//class ChatInterfaceFragment : Fragment() {
//    private lateinit var binding: FragmentChatInterfaceBinding
//    private lateinit var messageField: EditText
//    private lateinit var sendButton: Button
//    private lateinit var chatList: RecyclerView
//    private lateinit var adapter: CustomChatMessageAdapter
//    private lateinit var contactName: TextView
//    private var firebaseAuth = FirebaseAuth.getInstance()
//
//    private lateinit var currentUserUid: String
//    private lateinit var currentContactId: String
//
//    val chatItemList = mutableListOf<ChatMessage>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        binding = FragmentChatInterfaceBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Retrieve the current user's UID
//        currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
//
//        // Retrieve the current contact ID from arguments
//        currentContactId = arguments?.getString("contactId") ?: ""
//
//        getAllMessages()
//        // Initialize views
//        contactName = binding.tvContactName
//        messageField = binding.etChatMessage
//        sendButton = binding.btnSendMessage
//        chatList = binding.rvChat
//
//        // Set contact name text view
//        contactName.text = arguments?.getString("contactName")
//        val a = arguments?.getString("currentUserId")
//        val b = arguments?.getString("contactId")
//
//        println("a = ${a}")
//        println("b = ${b}")
//
//
//
//        listenToChanges()
//
//        // Initialize adapter and layout manager for chat list
////        adapter = CustomChatMessageAdapter(requireContext(), chatItemList)
////        chatList.adapter = adapter
////        chatList.layoutManager = LinearLayoutManager(requireContext())
//
//        // Set onClickListener for send button
//        sendButton.setOnClickListener {
//            val messageText = messageField.text.toString()
//            if (messageText.isNotEmpty()) {
//                // Save message using the currentContactId
//                saveMessage(messageText, currentContactId)
//                getAllMessages()
//                messageField.text.clear()
//            }
//        }
//    }
//
//    private fun saveMessage(message: String, currentContactId: String) {
//        if (currentContactId.isNotEmpty()) {
//            // Construct the path to the messages collection
//            val messagesCollectionPath = "users/$currentUserUid/contacts/$currentContactId/messages"
//
//            println("currentUserUid = ${currentUserUid}")
//            println("currentContactId = ${currentContactId}")
//
//            // Create data to store in Firestore
//            val messageData = hashMapOf(
//                "message" to message,
//                "senderId" to currentUserUid
//            )
//
//            // Add message document to Firestore under the messages collection path
//            FirebaseFirestore.getInstance().collection(messagesCollectionPath)
//                .add(messageData)
//                .addOnSuccessListener {
//                    Log.i("SUCCESS", "Message added to Firestore")
//                }
//                .addOnFailureListener { e ->
//                    Log.e("FAILURE", "Failed to add message to Firestore", e)
//                }
//        } else {
//            Log.e("ERROR", "Current contact ID is empty or null")
//        }
//    }
//
//
//    fun getAllMessages() {
//        val chatList = ArrayList<ChatMessage>()
//        val messagesCollectionSenderPath =
//            "users/$currentUserUid/contacts/$currentContactId/messages"
//        val messagesCollectionReceiverPath =
//            "users/$currentContactId/contacts/$currentUserUid/messages"
//
//        FirebaseFirestore
//            .getInstance()
//            .collection(messagesCollectionSenderPath)
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val text = document.getString("message")
//                    val sender = document.getString("senderId")
//                    val chatMessage = ChatMessage(text, sender)
//                    chatList.add(chatMessage)
//                }
//
//                Log.i("SUCCESS", "Successfully fetched all messages")
//                showMessages(chatList)
//            }.addOnFailureListener { Log.e("ERROR", "Error fetching messages") }
//
//        FirebaseFirestore
//            .getInstance()
//            .collection(messagesCollectionReceiverPath)
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val text = document.getString("message")
//                    val sender = document.getString("senderId")
//                    val chatMessage = ChatMessage(text, sender)
//                    chatList.add(chatMessage)
//                }
//
//                Log.i("SUCCESS", "Successfully fetched all messages")
//                showMessages(chatList)
//            }.addOnFailureListener { Log.e("ERROR", "Error fetching messages") }
//
//
//    }
//
//    private fun showMessages(chatMessageList: ArrayList<ChatMessage>) {
//        println("chatmessages = ${chatMessageList}")
//        adapter = CustomChatMessageAdapter(requireContext(), chatMessageList, currentUserUid)
//        chatList.adapter = adapter
//        chatList.layoutManager = LinearLayoutManager(requireContext())
//
//    }
//
//    fun listenToChanges() {
//        val instance = FirebaseFirestore.getInstance()
//        val messagesCollectionSenderPath =
//            "users/$currentUserUid/contacts/$currentContactId/messages"
//        val messagesCollectionReceiverPath =
//            "users/$currentContactId/contacts/$currentUserUid/messages"
//        val combinedChatMessages = ArrayList<ChatMessage>()
//        instance
//            .collection(messagesCollectionSenderPath)
//            .addSnapshotListener(requireActivity()) { value, error ->
//                if (error != null) {
//                    Log.e("ERROR", "failed to listen for updates")
//                }
//                if (value != null) {
//                    val chatList = ArrayList<ChatMessage>()
//                    for (document in value) {
//                        val chatMessage = document.toObject(ChatMessage::class.java)
//                        chatMessage?.let {
//                            combinedChatMessages.add(it)
//                        }
//                    }
//                    Log.i("SUCCESS", "Snapshot success")
//                }
//            }
//
//        instance
//            .collection(messagesCollectionReceiverPath)
//            .addSnapshotListener(requireActivity()) { value, error ->
//                if (error != null) {
//                    Log.e("ERROR", "failed to listen for updates")
//                }
//                if (value != null) {
//                    val chatList = ArrayList<ChatMessage>()
//                    for (document in value) {
//                        val chatMessage = document.toObject(ChatMessage::class.java)
//                        chatMessage?.let {
//                            combinedChatMessages.add(it)
//                        }
//                    }
//                    Log.i("SUCCESS", "Snapshot success")
//
//                }
//            }
//
//    }
//
//}
//
////val messagesCollectionSenderPath =
////    "users/$currentUserUid/contacts/$currentContactId/messages"
////val messagesCollectionReceiverPath =
////    "users/$currentContactId/contacts/$currentUserUid/messages"
////
////val collection1Ref =
////    FirebaseFirestore.getInstance().collection(messagesCollectionSenderPath)
////val collection2Ref =
////    FirebaseFirestore.getInstance().collection(messagesCollectionReceiverPath)
////val combinedChatMessages = ArrayList<ChatMessage>()
////
////collection1Ref.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
////    if (firebaseFirestoreException != null) {
////        println("Lyssnaren på samling 1 orsakade ett fel: $firebaseFirestoreException")
////        return@addSnapshotListener
////    }
////
////    // Hantera förändringar i samlingen 1 här
////    querySnapshot?.let { snapshot ->
////        for (document in snapshot.documents) {
////            val chatMessage = document.toObject(ChatMessage::class.java)
////            chatMessage?.let {
////                combinedChatMessages.add(it)
////            }
////
////        }
////    }
////}
////
////// Skapa en snapshotlistener för den andra samlingen
////collection2Ref.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
////    if (firebaseFirestoreException != null) {
////        println("Lyssnaren på samling 2 orsakade ett fel: $firebaseFirestoreException")
////        return@addSnapshotListener
////    }
////
////    // Hantera förändringar i samlingen 2 här
////    querySnapshot?.let { snapshot ->
////        val chatList = ArrayList<ChatMessage>()
////        for (document in snapshot.documents) {
////            val chatMessage = document.toObject(ChatMessage::class.java)
////            chatMessage?.let {
////                combinedChatMessages.add(it)
////            }
////        }
////    }
////}
//
//
