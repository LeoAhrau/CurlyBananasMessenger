package com.example.curlybananasmessenger

//import android.app.Application
//import androidx.room.Room
//import com.google.android.datatransport.runtime.dagger.Module
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//Kodsnutten använder Dagger-Hilt för att konfigurera och tillhandahålla kritiska databasresurser på ett
// sätt som underlättar testning, underhåll och skalbarhet i Android-applikationer. Det centraliserar
// skapandet av databasrelaterade objekt, minskar kopplingen mellan komponenterna och förbättrar kodens
// hanterbarhet och renhet. Genom att definiera dessa tillhandahållanden i en separat modul kan du enkelt
// ersätta eller ändra konfigurationer utan att påverka andra delar av applikationen.

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
