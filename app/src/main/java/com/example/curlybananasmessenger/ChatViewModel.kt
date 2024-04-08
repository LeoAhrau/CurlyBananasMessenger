package com.example.curlybananasmessenger

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers

//Skapandet av databasinstansen och användningen av dess DAO bör ske utanför AppDatabase-klassen,
// ofta inom en ViewModel, detta görs inte i AppDatabase-klassen själv.


class ChatViewModel(private val MessageDao: MessageDao) : ViewModel() { //ChatViewModel är en klass som ärver från ViewModel och fungerar som ett mellanlager mellan UI-komponenterna (t.ex. Activity eller Fragment) och datalagret (i detta fall representerat av MessageDao för databasåtkomst). Det hanterar UI-relaterad data på ett livscykelmedvetet sätt, vilket innebär att det hjälper till att hantera data på ett sätt som är resistent mot konfigurationsändringar (som skärmrotationer).

    // Funktion för att infoga ett meddelande
    fun insertMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        MessageDao.insert(message) //I det här exemplet använder vi viewModelScope för att starta coroutines. Det garanterar att alla
// asynkrona operationer avbryts när ViewModel-instansen förstörs, vilket hjälper till att förebygga minnesläckor.
    }

    // Funktion för att hämta meddelanden som LiveData
     fun getMessagesForUser(userId: Int) = MessageDao.getMessagesForUser(userId)
}
