package com.example.curlybananasmessenger

//import android.app.Application
//import androidx.room.Room
//import com.google.android.datatransport.runtime.dagger.Module
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DatabaseModule {
//
//    @Provides
//    @Singleton
//    fun provideDatabase(application: Application): ContactsDatabase {
//        return Room.databaseBuilder(
//            application.applicationContext,
//            ContactsDatabase::class.java,
//            "contacts_database"
//        ).fallbackToDestructiveMigration().build()
//    }
//
//    @Provides
//    fun provideContactsDao(database: ContactsDatabase): ContactsDao {
//        return database.contactsDao()
//    }
//
//    @Provides
//    fun provideContactsRepository(contactsDao: ContactsDao): ContactsRepository {
//        return ContactsRepository(contactsDao)
//    }
//}
