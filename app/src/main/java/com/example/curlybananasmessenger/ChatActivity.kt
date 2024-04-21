package com.example.curlybananasmessenger

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.curlybananasmessenger.databinding.ActivityChatBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.util.UUID


class ChatActivity : BaseActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var messageViewModel: MessageViewModel
    private var idList: List<String>? = listOf()

    private val KEY_ID = "id"
    private val KEY_TEXT = "message"
    val KEY_SENDER = "user_sender"
    val KEY_RECEIVER = "user_receiver"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkFireStoreCollection()

        val sendButton: Button = findViewById(R.id.sendButton)

        messageViewModel = ViewModelProvider(this)[MessageViewModel::class.java]

        messageViewModel.getAllMessageIds().observe(this) { ids ->
            idList = ids
        }

        messageViewModel.getEveryMessage().observe(this) { messages ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, messages)
            binding.lvList.adapter = adapter
        }

        sendButton.setOnClickListener {
           addMessage()
        }
    }

    private fun addMessage() {
        try {
            val text = binding.messageEditText.text.toString()
            val id = UUID.randomUUID().toString()
            val message =
                Message(id = id, message = text, userSender = id, userReceiver = UUID.randomUUID().toString())
            messageViewModel.addToFireStore(message)
            binding.messageEditText.text.clear()

        } catch (e: Exception) {
            println(e.stackTrace)
            Toast.makeText(this, "Error adding user to database", Toast.LENGTH_SHORT).show()
        }
    }
    private fun checkFireStoreCollection() {
        FirebaseFirestore
            .getInstance()
            .collection("messagesTest")
            .addSnapshotListener(this) { value, error ->
                if (error != null) {
                    Log.e("ERROR", "failed to listen for updates")
                }
                if (value != null) {
                    for (document in value) {
                        val id = document.getString(KEY_ID) as String
                        val text = document.getString(KEY_TEXT) as String
                        val userSender = document.getString(KEY_SENDER) as String
                        val userReceiver = document.getString(KEY_RECEIVER) as String
                        val message = Message(id, text, userSender, userReceiver)

                        if (idList?.contains(document.id) == false) {
                            messageViewModel.insert(message)
                        }
                    }
                    Log.i("SUCCESS", "Snapshot success")
                }
            }
    }
}



//
//import android.os.Bundle
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//
//
//class ChatActivity : AppCompatActivity() {
//
//    private lateinit var viewModel: ChatViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState) //Inuti onCreate, skapas en instans av ChatViewModel med hjälp av en ViewModelProvider. Detta möjliggör för ChatActivity att observera dataändringar och uppdatera UI:t enligt dessa ändringar.
//        setContentView(R.layout.activity_chat)
//
//        // Skapa en instans av ViewModel
//        val factory = ChatViewModelFactory(AppDatabase.getDatabase(application).messageDao()) //För att skapa ViewModel, används en fabrik (ChatViewModelFactory) som kräver en instans av MessageDao från databasen. Detta tillhandahålls genom att kalla på AppDatabase.getDatabase(application).messageDao().
//        viewModel = ViewModelProvider(this, factory).get(ChatViewModel::class.java)
//
//        // Observera meddelanden för en användare
//        viewModel.getMessagesForUser(1).observe(this) { messages -> //Med detta observerar aktiviteten ändringar i meddelandelistan för en specifik användare (i detta fall användare med ID 1). När datan ändras, uppdateras UI:t för att visa de nya meddelandena.
//            val allMessagesAsString = messages.joinToString(separator = "\n") { message -> //De observerade meddelandena konverteras till en sträng som representerar alla meddelanden, med hjälp av joinToString. Varje meddelande formateras med dess tidsstämpel och innehåll, separerade av en ny rad.
//                "${message.timestamp}: ${message.content}"
//            }
//            findViewById<TextView>(R.id.messagesTextView).text = allMessagesAsString
//        }
//
//
//        // Exempel på att lägga till ett meddelande
//        val newMessage = Message(userId = 1, content = "Hello, how are you?", timestamp = System.currentTimeMillis())
//        viewModel.insertMessage(newMessage)
//    }
//}
////Detta kodblock demonstrerar en grundläggande användning av ViewModel och LiveData (eller Flow) för att skapa en reaktiv användargränssnittskomponent som automatiskt uppdateras när den underliggande datan ändras. Det illustrerar också hur man kan interagera med en lokal databas i en Android-applikation genom Room-biblioteket.