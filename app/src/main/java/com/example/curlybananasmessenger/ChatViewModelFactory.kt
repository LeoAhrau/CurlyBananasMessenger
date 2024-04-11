package com.example.curlybananasmessenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
//ChatViewModelFactory är en anpassad implementation av ViewModelProvider.Factory, som används för att
// skapa ViewModel-instanser med specifika konstruktorparametrar. Detta är nödvändigt eftersom standardfabriken inte kan hantera konstruktorparametrar.

class ChatViewModelFactory(private val messageDao: MessageDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(messageDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
//create-funktionen: Metoden create överlagras för att returnera en instans av ChatViewModel. Den kontrollerar först att den begärda ViewModel-klassen kan
// tilldelas från ChatViewModel-klassen. Om det stämmer, returneras en ny instans av ChatViewModel med messageDao som argument. Detta mönster används för att injicera beroenden i ViewModel-klasser.