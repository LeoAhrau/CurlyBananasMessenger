package com.example.curlybananasmessenger

import androidx.lifecycle.LiveData


class ContactsRepository(private val contactsDao: ContactsDao) {
    val allContacts: LiveData<List<Contacts>> = contactsDao.getAllContacts()

    suspend fun insert(contact: Contacts) {
        contactsDao.insertContact(contact)
    }
}

//
//class ContactsRepository(private val contactsDao: ContactsDao) {
//    val allContacts: LiveData<List<Contact>> = contactsDao.getAllContacts()
//
//    suspend fun insert(contacts: Contacts) {
//        contactsDao.insertContacts(contacts)
//    }
//}
