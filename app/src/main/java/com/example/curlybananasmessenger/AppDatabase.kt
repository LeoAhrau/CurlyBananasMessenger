package com.example.curlybananasmessenger

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase

//Detta är en annotering som markerar klassen som en Room-databas. Den definierar vilka entiteter (tabeller) databasen ska innehålla, vilken version databasen är och om schemat ska exporteras eller inte.
@Database(entities = [Message::class, User::class, Conversation::class, Contacts::class], version = 1, exportSchema = false) //entities = [Message::class]: Detta specificerar att Message klassen är en entitet som ska lagras i databasen. exportSchema = false: Detta förhindrar att Room exporterar databasens schema till en JSON-fil, vilket kan vara användbart för versionkontroll
abstract class AppDatabase : RoomDatabase() {  //version = 1: Detta anger versionen av databasen, vilket är användbart för att hantera databasuppgraderingar genom migrations.
    abstract fun messageDao(): MessageDao

    abstract fun userDao(): UserDao

    abstract fun conversationDao(): ConversationDao

    companion object {
    // Singleton förhindrar flera instanser av databasen att öppnas samtidigt.
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase { //DatabasInstansiering Forst kontrolelras om en instans av DB redan finns med getDatabase, om den finns returneras den befintliga instansen
        // Om INSTANCE inte är null, returnera den,
        // annars skapa databasen
        return INSTANCE ?: synchronized(this) { //synchronized: Om ingen instans finns, skapas en ny i ett synkroniserat block, vilket säkerställer att endast en tråd åt gången kan utföra detta kodblock.
            val instance = Room.databaseBuilder(  //Room.databaseBuilder: Detta bygger en ny databasinstans med hjälp av Room biblioteket. context.applicationContext säkerställer att en applikationsomfattande Context används istället för en aktivitetsspecifik, vilket kan förhindra minnesläckor.
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).fallbackToDestructiveMigration().build()  //fallbackToDestructiveMigration(): Om en migration behövs och ingen migrationsstrategi är definierad, kommer databasen att rensas och återskapas istället för att uppdateras.
            INSTANCE = instance
            // returnera instansen
            instance
        }
    }
}
}

//Sammantaget skapar detta kodblock en säker och effektiv tillgång till en Room-databas med
// hjälp av en singleton-design för att undvika flera instanser av databasen, vilket är viktigt för att undvika konflikter och potentiella fel i en flertrådad miljö.
