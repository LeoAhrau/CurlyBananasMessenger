package com.example.curlybananasmessenger

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider



class ChatActivity : BaseActivity() {
    private lateinit var messageViewModel: MessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val messageEditText: EditText = findViewById(R.id.messageEditText)
        val sendButton: Button = findViewById(R.id.sendButton)
        val lastMessageTextView: TextView = findViewById(R.id.lastMessageTextView)

        val dao = AppDatabase.getDatabase(application).messageDao()
        val repository = MessageRepository(dao)
        messageViewModel = ViewModelProvider(this, ViewModelFactory(repository))[MessageViewModel::class.java]

        sendButton.setOnClickListener {
            val messageText = messageEditText.text.toString()
            if (messageText.isNotEmpty()) {
                val message = Message(message = messageText)
                messageViewModel.insert(message)
                messageEditText.setText("")

                // Uppdatera TextView med det senast inskickade meddelandet
                lastMessageTextView.text = "Senast sparad: $messageText"
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