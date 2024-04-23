package com.example.curlybananasmessenger

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactsDao {
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): LiveData<List<Contacts>>

    @Insert
    fun insertContact(contact: Contacts)  // Changed from insertContacts to insertContact

    @Query("SELECT * FROM contacts ORDER BY id DESC LIMIT 1")
    fun getLastContact(): LiveData<Contacts>  // Changed from getLastContacts to getLastContact
}



//Define a DAO to access your database operations. This interface will provide methods for inserting,
// fetching, and other operations on the contacts.
//interface ContactsDao {
//    @Query("SELECT * FROM contacts")
//    fun getAllContacts(): LiveData<List<Contacts>>
//
//    @Insert
//    fun insertContacts(contacts: Contacts)
//
//    @Query("SELECT * FROM contacts ORDER BY id DESC LIMIT 1")
//    fun getLastContacts(): LiveData<Contacts>
//}
//
//
