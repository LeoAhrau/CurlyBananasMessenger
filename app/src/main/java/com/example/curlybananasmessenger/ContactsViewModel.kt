package com.example.curlybananasmessenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//
//class ContactsViewModel(private val repository: ContactsRepository) : ViewModel() {
//    val allContacts: LiveData<List<Contacts>> = repository.allContacts
//
//    fun insert(contact: Contacts) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(contact)
//    }
//}
//
//

//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//import com.google.firebase.firestore.FirebaseFirestore
//
//open class ContactsViewModel(Repository: Any?) : ContactsViewModel(com.example.curlybananasmessenger.ContactsRepository) {
//    val allContacts: Any
//    private val db = FirebaseFirestore.getInstance()
//
//    fun fetchContactsAndStore() {
//        viewModelScope.launch {
//            db.collection("contacts")
//                .get()
//                .addOnSuccessListener { result ->
//                    val newContacts = result.map { doc ->
//                        Contacts(
//                            id = doc.id,
//                            name = doc.getString("name") ?: "",
//                            email = doc.getString("email")
//                        )
//                    }
//                    storeContactsInLocalDatabase(newContacts)
//                }
//                .addOnFailureListener { exception ->
//                    // Handle error
//                }
//        }
//    }
//
//    private fun storeContactsInLocalDatabase(contacts: List<Contact>) {
//        viewModelScope.launch {
//            val database = Room.databaseBuilder(App.instance, AppDatabase::class.java, "mycontacts.db").build()
//            database.contactDao().insertAll(contacts)
//        }
//    }
//}


////import androidx.lifecycle.ViewModel
////import androidx.lifecycle.viewModelScope
////import androidx.room.Room
////import kotlinx.coroutines.launch
////import com.google.firebase.firestore.FirebaseFirestore
//
////Fetch contacts from Firebase Firestore and store them in your Room database. You can use Kotlin
//// Coroutines for better asynchronous handling.
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//import kotlinx.coroutines.withContext
//
//class ContactsViewModel : ViewModel() {
//    private val db = FirebaseFirestore.getInstance()
//
//    fun fetchContactsAndStore() {
//        viewModelScope.launch {
//            try {
//                val result = db.collection("contacts").get().await()
//                val contacts = result.documents.map { doc ->
//                    Contact(
//                        id = doc.id,
//                        name = doc.getString("name") ?: "",
//                        email = doc.getString("email"),
//
//                    )
//                }
//                storeContactsInLocalDatabase(contacts)
//            } catch (exception: Exception) {
//                // Handle error, log it or inform user
//            }
//        }
//    }
//
//    private suspend fun storeContactsInLocalDatabase(contacts: List<Contact>) {
//        withContext(Dispatchers.IO) { // Explicitly shifting to IO thread for DB operations
//            val database = AppDatabase.DatabaseProvider.getDatabase(App.instance)
//            database.contactDao().insertAll(contacts)
//        }
//    }
//}
//
//
//
////class ContactsViewModel : ViewModel() {
////    private val db = FirebaseFirestore.getInstance()
////
////    fun fetchContactsAndStore() {
////        viewModelScope.launch {
////            db.collection("contacts")
////                .get()
////                .addOnSuccessListener { result ->
////                    val contacts = result.map { doc ->
////                        Contact(
////                            id = doc.id,
////                            name = doc.getString("name") ?: "",
////                            email = doc.getString("email"),
////                            phoneNumber = doc.getString("phoneNumber"),
////                            address = doc.getString("address")
////                        )
////                    }
////                    storeContactsInLocalDatabase(contacts)
////                }
////                .addOnFailureListener { exception ->
////                    // Handle error
////                }
////        }
////    }
////
//////    private fun storeContactsInLocalDatabase(contacts: List<Contact>) {
//////        viewModelScope.launch {
//////            val database = Room.databaseBuilder(App.instance, AppDatabase::class.java, "mycontacts.db").build()
//////            database.contactDao().insertAll(contacts)
//////        }
//////    }
////    private fun storeContactsInLocalDatabase(contacts: List<Contact>) {
////        viewModelScope.launch {
////            val database = AppDatabase.DatabaseProvider.getDatabase(App.instance)
////            database.contactDao().insertAll(contacts)
////        }
////    }
////
////}
