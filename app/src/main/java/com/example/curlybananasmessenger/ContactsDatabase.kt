package com.example.curlybananasmessenger

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [Contacts::class], version = 1, exportSchema = false)
//abstract class ContactsDatabase : RoomDatabase() {
//    abstract fun contactsDao(): ContactsDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: ContactsDatabase? = null
//
//        fun getDatabase(context: Context): ContactsDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ContactsDatabase::class.java,
//                    "contacts_database"
//                ).fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
//

//
//@Database(entities = [Contact::class], version = 1, exportSchema = false)
//abstract class ContactDatabase : RoomDatabase() {
//    abstract fun contactDao(): ContactDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: ContactDatabase? = null
//
//        fun getDatabase(context: Context): ContactDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ContactDatabase::class.java,
//                    "contact_database"
//                ).fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
