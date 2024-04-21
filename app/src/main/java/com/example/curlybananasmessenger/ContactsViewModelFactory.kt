package com.example.curlybananasmessenger

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContactsViewModelFactory(private val application: Application, private val repository: ContactsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//class ContactsViewModelFactory(
//    private val application: Application,
//    private val repository: ContactsRepository
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ContactsViewModel(application, repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
// ContactsViewModelFactory(private val repository: ContactsRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return ContactsViewModel(ContactsRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
