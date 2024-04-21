package com.example.curlybananasmessenger

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import javax.inject.Inject

//Correct the Constructor Parameter: Make sure to pass the Application object to AndroidViewModel(?) and use a ContactsRepository for repository operations.
//Hur du använder ContactsViewModelFactory
//När du skapar din ViewModel från en aktivitet eller ett fragment, måste du använda ContactsViewModelFactory med både Application och ContactsRepository!!

class ContactsViewModel(application: Application, private val repository: ContactsRepository) : AndroidViewModel(application) {

    // Expose LiveData from the repository to be observed by the UI
    val allContacts: LiveData<List<Contacts>> = repository.getAllContacts()

    // Method to insert a contact
    fun insert(contact: Contacts) {
        repository.insert(contact)
    }

    // Static method to create ViewModel with the necessary dependencies
    companion object {
        fun create(context: Context): ContactsViewModel {
            val db = Room.databaseBuilder(
                context.applicationContext,
                ContactsDatabase::class.java,
                "contacts_database"
            ).fallbackToDestructiveMigration().build()

            val contactsDao = db.contactsDao()
            val repository = ContactsRepository(contactsDao)

            return ContactsViewModel(context.applicationContext as Application, repository)
        }
    }
}



//class ContactsViewModel(application: Application, private val repository: ContactsRepository) : AndroidViewModel(application) {
//
//    val factory = ContactsViewModelFactory(application, repository)
//    val viewModel = ViewModelProvider(this, factory).get(ContactsViewModel::class.java)
//
//    private fun ViewModelProvider(
//        contactsViewModel: ContactsViewModel,
//        factory: ContactsViewModelFactory,
//    ): Any {
//
//    }
//
//
//    init {
//        val db = Room.databaseBuilder(
//            application.applicationContext,
//            ContactsDatabase::class.java,
//            "contacts_database"
//        ).fallbackToDestructiveMigration().build()
//
//        val contactsDao = db.contactsDao()
//        repository = ContactsRepository(contactsDao)
//    }
//
//    val allContacts: LiveData<List<Contacts>> = repository.getAllContacts()
//
//    fun insert(contact: Contacts) {
//        repository.insert(contact)
//    }
//}
//


//@HiltViewModel
//class ContactsViewModel @Inject constructor(
//    private val repository: ContactsRepository
//) : ViewModel() {
//
//    val allContacts: LiveData<List<Contacts>> = repository.getAllContacts()
//
//    fun insert(contact: Contacts) {
//        repository.insert(contact)
//    }
//}

//class ContactsViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {
//
//    fun saveContact(contact: Contact) {
//        firestoreRepository.addOrUpdateContact(contact)
//    }
//}



//
//class ContactsViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: ContactsRepository
//
//    init {
//        val db = Room.databaseBuilder(
//            application.applicationContext,
//            ContactsDatabase::class.java,
//            "contacts_database"
//        ).fallbackToDestructiveMigration().build()
//
//        val contactsDao = db.contactsDao()
//        repository = ContactsRepository(contactsDao)
//    }
//
//    val allContacts: LiveData<List<Contacts>> = repository.getAllContacts()
//
//    fun insert(contact: Contacts) {
//        repository.insert(contact)
//    }
//}
//


//class ContactsViewModel(application: Application) : AndroidViewModel(application) {
//    private val contactsDao: ContactsDao
//    private val repository: ContactsRepository
//
//    init {
//        val db = Room.databaseBuilder(
//            application.applicationContext,
//            ContactsDatabase::class.java,
//            "contacts_database"
//        ).fallbackToDestructiveMigration().build()
//
//        contactsDao = db.contactsDao()
//        repository = ContactsRepository(contactsDao)
//    }
//
//    val allContacts: LiveData<List<Contacts>> = repository.getAllContacts()
//
//    fun insert(contacts: Contacts) = repository.insert(contacts)


//class ContactsViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: ContactsRepository
//
//    init {
//        // Initialize your repository here
//        val contactsDao = ContactsDatabase.getDatabase(application).contactsDao()
//        repository = ContactsRepository(contactsDao)
//    }
//
//    val contacts: LiveData<List<Contacts>> = repository.getAllContacts()
//
//    fun insert(contacts: Contacts) {
//        repository.insert(contacts)
//    }
//}
//


//class ContactsViewModel(application: ContactsRepository) : AndroidViewModel(application) {
//    fun insert(contacts: Contacts) {
//
//    }
//
//    private val contactsDao: ContactsDao = ContactsDatabase.getDatabase(application).contactsDao()
//    val contacts: LiveData<List<Contacts>> = contactsDao.getAllContacts()
//}

//class ContactsViewModel(private val repository: ContactsRepository) : ViewModel() {
//    val allContacts: LiveData<List<Contacts>> = repository.allContacts
//
//    fun insert(contact: Contacts) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(contact)
//    }
//}
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
