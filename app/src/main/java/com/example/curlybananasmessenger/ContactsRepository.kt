package com.example.curlybananasmessenger

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//Create a function to adapt a Contact object to a Contacts object (adjust field mappings as necessary). Contacts is the class used for the database and Contact is used for Firestore, let's set up a way to convert between them when needed:

class ContactsRepository(private val contactsDao: ContactsDao) {

    init {
        initFirestoreDataSync()
    }

    private fun initFirestoreDataSync() {
        FirebaseFirestore.getInstance().collection("contacts")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("ContactsRepository", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val roomContacts = value?.map { document ->
                    Contact(
                        contactId = document.id,
                        contactName = document.getString("name"),
                        contactEmail = document.getString("email")
                    )
                }?.map(::adaptToContacts) ?: listOf()

                CoroutineScope(Dispatchers.IO).launch {
                    roomContacts.forEach { contact ->
                        contactsDao.insertContacts(contact)
                    }
                }
            }
    }

    private fun adaptToContacts(contact: Contact): Contacts {
        return Contacts(
            firestoreId = contact.contactId ?: "", // Använd Firestore ID direkt som en sträng
            name = contact.contactName ?: "",
            email = contact.contactEmail ?: ""
        )
    }


    fun getAllContacts(): LiveData<List<Contacts>> = contactsDao.getAllContacts()

    fun insert(contact: Contacts) {
        CoroutineScope(Dispatchers.IO).launch {
            contactsDao.insertContacts(contact)
        }
    }
}



//class ContactsRepository(private val contactsDao: ContactsDao) {
//
//    init {
//        initFirestoreDataSync()
//    }
//    private fun adaptToContacts(contact: Contact): Contacts {
//        return Contacts(
//            id = contact.contactId?.toInt() ?: 0,
//            name = contact.contactName ?: "",
//            email = contact.contactEmail ?: ""
//        )
//    }
//    fun getAllContacts(): LiveData<List<Contacts>> = contactsDao.getAllContacts()
//
//    fun insert(contacts: Contacts) {
//        CoroutineScope(Dispatchers.IO).launch {
//            contactsDao.insertContacts(contacts) // Säkerställ att denna metod finns i din DAO med korrekta parametrar
//        }
//    }
//
//    private fun initFirestoreDataSync() {
//        FirebaseFirestore.getInstance().collection("contacts")
//            .addSnapshotListener { value, e ->
//                if (e != null) {
//                    Log.w("ContactsRepository", "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//
//                val roomContacts = value?.map { document ->
//                    Contacts(
//                        id = document.id.toInt(),
//                        name = document.getString("name") ?: "",
//                        email = document.getString("email") ?: ""
//                    )
//                } ?: listOf()
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    roomContacts.forEach { contact ->
//                        contactsDao.insertContacts(contacts) // Antar att detta accepterar ett enskilt Contacts-objekt
//                    }
//                }
//            }
//    }
//}


//class ContactsRepository(application: Application) {
//    private val db: ContactsDatabase = Room.databaseBuilder(
//        application.applicationContext,
//        ContactsDatabase::class.java,
//        "contacts_database"
//    ).fallbackToDestructiveMigration()
//        .build()
//
//    private val contactsDao: ContactsDao = db.contactsDao()
//
//    init {
//        initFirestoreDataSync()
//    }
//
//    fun getAllContacts(): LiveData<List<Contacts>> = contactsDao.getAllContacts()
//
//    fun insert(contacts: Contacts) {
//        CoroutineScope(Dispatchers.IO).launch {
//            contactsDao.insertContacts(contacts)
//        }
//    }
//
//    private fun initFirestoreDataSync() {
//        FirebaseFirestore.getInstance().collection("contacts")
//            .addSnapshotListener { value, e ->
//                if (e != null) {
//                    Log.w("ContactsRepository", "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//
//                val roomContacts = value?.map { document ->
//                    Contacts(
//                        id = document.id.toInt(),
//                        name = document.getString("name") ?: "",
//                        email = document.getString("email") ?: ""
//                    )
//                } ?: listOf()
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    contactsDao.insertContacts(roomContacts)
//                }
//            }
//    }
//}




//
//class ContactsRepository(application: Application) {
//
//    private val db: ContactsDatabase = Room.databaseBuilder(
//        application.applicationContext,
//        ContactsDatabase::class.java,
//        "contacts_database" // Uppdaterad databasnamn
//    ).fallbackToDestructiveMigration() // Det är bra att ha denna om ingen migrationsplan finns.
//        .build()
//
//    private val contactsDao: ContactsDao = db.contactsDao()
//
//    init {
//        initFirestoreDataSync()
//    }
//
//    private fun initFirestoreDataSync() {
//        FirebaseFirestore.getInstance().collection("contacts")
//            .addSnapshotListener { value, e ->
//                if (e != null) {
//                    Log.w("ContactsRepository", "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//
//                val roomContacts = value?.map { document ->
//                    Contacts(
//                        id = document.id.toInt(),
//                        name = document.getString("name") ?: "",
//                        email = document.getString("email") ?: ""
//                    )
//                } ?: listOf()
//
//                // Använd Coroutine för att spara till Room-databasen
//                CoroutineScope(Dispatchers.IO).launch {
//                    contactsDao.insertContacts(roomContacts)
//                }
//            }
//    }
//}
//

//class ContactsRepository(application: Application) {
//
//    private val contactsDao: ContactsDao
//    private val db: ContactsDatabase = Room.databaseBuilder(
//        application.applicationContext,
//        ContactsDatabase::class.java, "database-name"
//    ).build()
//
//    init {
//        contactsDao = db.contactsDao()
//        initFirestoreDataSync()
//    }
//
//    private fun initFirestoreDataSync() {
//        FirebaseFirestore.getInstance().collection("contacts")
//            .addSnapshotListener { value, e ->
//                if (e != null) {
//                    Log.w("ContactsRepository", "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//
//                val roomContacts = value?.map { document ->
//                    Contacts(
//                        id = document.id.toInt(),
//                        name = document.getString("name") ?: "",
//                        email = document.getString("email") ?: ""
//                    )
//                } ?: listOf()
//
//                // Använd Coroutine för att spara till Room-databasen
//                CoroutineScope(Dispatchers.IO).launch {
//                    contactsDao.insertContacts(roomContacts)
//                }
//            }
//    }
//}
//


//class ContactsRepository(private val contactsDao: ContactsDao) {
//    val allContacts: LiveData<List<Contacts>> = contactsDao.getAllContacts()
//
//    suspend fun insert(contact: Contacts) {
//        contactsDao.insertContact(contact)
//    }
//}

//
//class ContactsRepository(private val contactsDao: ContactsDao) {
//    val allContacts: LiveData<List<Contact>> = contactsDao.getAllContacts()
//
//    suspend fun insert(contacts: Contacts) {
//        contactsDao.insertContacts(contacts)
//    }
//}
