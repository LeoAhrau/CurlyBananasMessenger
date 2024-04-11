package com.example.curlybananasmessenger

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
//@Database är en annotation som definierar en databas inom Room-biblioteket. Det berättar för Room att den här databasen kommer att innehålla entiteter av typen Message
@Database(entities = [Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao //en abstrakt klass som ärver från RoomDatabase. Detta är en del av Room-bibliotekets design, där du definierar en abstrakt databasklass som innehåller abstrakta metoder för att hämta DAOs (Data Access Objects). I detta fall finns det en metod messageDao() som förväntas returnera en instans av MessageDao, ett interface för att hantera databasoperationer relaterade till Message-entiteter.
//
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null //Singleton-mönstret: Inom AppDatabase används Singleton-mönstret för att säkerställa att endast en instans av databasen skapas under appens livstid. Detta uppnås genom att hålla en privat statisk referens INSTANCE till databasinstansen och kontrollera att den inte är null när getDatabase-metoden anropas. Om INSTANCE är null, skapas en ny databasinstans med buildDatabase-metoden. Användningen av @Volatile säkerställer att ändringarna av INSTANCE är synliga i alla trådar.

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) { //Synkroniserad block: synchronized(this) används för att säkerställa att endast en tråd åt gången kan exekvera kodblocket som skapar databasinstansen. Detta förhindrar att flera instanser av databasen skapas när flera trådar försöker få tillgång till databasen samtidigt.
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "chat_app_database"
            ).build()
    }
}
